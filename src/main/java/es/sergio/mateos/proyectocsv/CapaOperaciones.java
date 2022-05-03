package es.sergio.mateos.proyectocsv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CapaOperaciones {
    
    ImageView banner;
    String paisActual = "";
    String annoActual = "";
    
    String atentadosObtenidos = "";
    int atentadosTotales = 0;
    int numeroSumasMedia = 0;
    float media = 0;
    
    short contadorPaises = 0;
    short contadorAnnos = 0;
    
    boolean annadirAnno = true;
    boolean mostrarRegistro = false;
    boolean pulsacionSearch = false;
    
    String seleccionPais = "";
    String seleccionAnno = "";
    
     ExportarContenido exportarContenido = new ExportarContenido();
    
    public void leerFichero(HBox hBoxComboBox, HBox hBoxDatos, HBox hBoxMediaAtentados, VBox root, boolean depuracion){
        /////////////CREAMOS LA LISTA QUE CONTIENE LOS VALORES DESPLEGABLES 1////////////////
        ArrayList<String> listaPais = new ArrayList();
        ArrayList<String> listaAnno = new ArrayList();
        /////////////////////////////////////////////////////////////////////////////////////
        
        String nombreFichero = "fatalities-from-terrorism.csv";
        // Declarar una variable BufferedReader
        BufferedReader br = null;
        try {
            // Crear un objeto BufferedReader al que se le pasa 
            //   un objeto FileReader con el nombre del fichero
            br = new BufferedReader(new FileReader(nombreFichero));
            // Leer la primera línea, guardando en un String
            String texto = br.readLine();
            // Repetir mientras no se llegue al final del fichero
            while(texto != null) {
                String[] valores = texto.split(",");
                CapaDatos capaDatos = new CapaDatos();
                capaDatos.setAtentados(valores[3]);
                capaDatos.setAnno(valores[2]);
                capaDatos.setPais(valores[0]);
                if(!paisActual.equals(capaDatos.getPais()) && !capaDatos.getPais().equals("Entity") && !capaDatos.getPais().isEmpty()){
                    paisActual = capaDatos.getPais();
                    listaPais.add(paisActual);
                    contadorPaises++;
                }
                
                for(int i=0;i<listaAnno.size();i++){
                    if(capaDatos.getAnno().equals(listaAnno.get(i))){
                        annadirAnno = false;
                    }
                }
                if(annadirAnno==true && !capaDatos.getAnno().equals("Year")){
                    listaAnno.add(capaDatos.getAnno());
                    contadorAnnos++;
                }
                // Leer la siguiente línea
                texto = br.readLine();
            }
            if(depuracion == true){
                System.out.println("Se han añadido "+contadorPaises+" entradas al comboBox de paises");
                System.out.println("Se han añadido "+contadorAnnos+" entradas al comboBox de años");
            }
        }
        // Captura de excepción por fichero no encontrado
        catch (FileNotFoundException ex) {
            System.out.println("Error: Fichero no encontrado");
            ex.printStackTrace();
        }
        // Captura de cualquier otra excepción
        catch(Exception ex) {
            System.out.println("Error de lectura del fichero");
            ex.printStackTrace();
        }
        // Asegurar el cierre del fichero en cualquier caso
        finally {
            try {
                // Cerrar el fichero si se ha podido abrir
                if(br != null) {
                    br.close();
                }
            }
            catch (Exception ex) {
                System.out.println("Error al cerrar el fichero");
                ex.printStackTrace();
            }
        }
        //COMBOBOX PAIS //////////////////////////////////////////
        ComboBox<String> comboBoxPais = new ComboBox(FXCollections.observableList(listaPais));
        hBoxComboBox.getChildren().add(comboBoxPais);
        comboBoxPais.setPromptText("Seleccionar país");

        // Añadir un label en el que se mostrará el elemento seleccionado
        Label paisSeleccionado = new Label();
        hBoxDatos.getChildren().add(paisSeleccionado);

            // Cuando el usuario seleccione algo del ComboBox, se mostrará en el Label
        comboBoxPais.setOnAction((t) -> {
            seleccionPais = comboBoxPais.getValue();
            if(depuracion == true){
                System.out.println("País seleccionado: "+seleccionPais);
            }
        });
        //////////////////////////////////////////////////////////
        //COMBOBOX AÑO //////////////////////////////////////////
        Collections.sort(listaAnno); // ORDENA EL ARRAYLIST POR ORDEN ALFABETICO
        ComboBox<String> comboBoxAnno = new ComboBox(FXCollections.observableList(listaAnno));
        hBoxComboBox.getChildren().add(comboBoxAnno);
        comboBoxAnno.setPromptText("Seleccionar año");

        // Añadir un label en el que se mostrará el elemento seleccionado
        Label annoSeleccionado = new Label();
        hBoxDatos.getChildren().add(annoSeleccionado);

        // Cuando el usuario seleccione algo del ComboBox, se mostrará en el Label
        comboBoxAnno.setOnAction((t) -> {
            seleccionAnno = comboBoxAnno.getValue();
            if(depuracion == true){
                System.out.println("Año seleccionado: "+seleccionAnno);
            }
            
        });
        /////////// APARTADO DE LABELS ///////////////////////////
        Label numeroAtentadosLabel = new Label();
        root.getChildren().add(numeroAtentadosLabel);
        
        Label mediaAtentadosLabel = new Label();
        root.getChildren().add(mediaAtentadosLabel);
        
        //////////////////////////////////////////////////////////
        
        //////////// APARTADO DE BOTONES /////////////////////////
        Button buttonSearch = new Button("Buscar");
        hBoxComboBox.getChildren().add(buttonSearch);
        
        Button buttonExport = new Button("Exportar");
        root.getChildren().add(buttonExport);
        
        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent t) {
                if(depuracion == true){
                    System.out.println("Se ha pulsado el botón Buscar");
                }
                //////////////////////////////////////
                //String nombreFichero = "fatalities-from-terrorism.csv";
                // Declarar una variable BufferedReader
                BufferedReader br = null;
                try {
                    // Crear un objeto BufferedReader al que se le pasa
                    //   un objeto FileReader con el nombre del fichero
                    br = new BufferedReader(new FileReader(nombreFichero));
                    // Leer la primera línea, guardando en un String
                    String texto = br.readLine();
                    // Repetir mientras no se llegue al final del fichero
                    while(texto != null) {
                        String[] valores = texto.split(",");
                        CapaDatos capaDatos = new CapaDatos();
                        capaDatos.setAtentados(valores[3]);
                        capaDatos.setAnno(valores[2]);
                        capaDatos.setPais(valores[0]);
                        
                        if(capaDatos.getPais().equals(seleccionPais) && capaDatos.getAnno().equals(seleccionAnno)){
                            atentadosObtenidos = capaDatos.getAtentados();
                            mostrarRegistro = true;
                        }
                        if(capaDatos.getPais().equals(seleccionPais)){
                            atentadosTotales += Integer.parseInt(capaDatos.getAtentados());
                            numeroSumasMedia++;
                        }
                        // Leer la siguiente línea
                        texto = br.readLine();
                    }
                    if(mostrarRegistro==true){
                        numeroAtentadosLabel.setText("Numero de atentados en "+seleccionPais+" a lo largo de "+seleccionAnno+": "+atentadosObtenidos);
                        mostrarRegistro = false;
                    }else{
                        if(depuracion == true){
                            System.out.println("Llamando Alert de 'Error de búsqueda'");
                        }
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error en la búsqueda");
                        alert.setHeaderText("No se ha encontrado ningún registro");
                        alert.setContentText("Ooops, parece que no se ha encontrado ningún registro con esos criterios de búsqueda. Si has seleccionado un país obtendrás la media de atentados en ese país");

                        alert.showAndWait();
                    }
                    if(depuracion == true){
                        System.out.println("Obteniendo media de valores...");
                    }
                    media = 0;
                    media = atentadosTotales/numeroSumasMedia;
                    mediaAtentadosLabel.setText("Media de atentados en "+seleccionPais+" a lo largo de la historia: "+media);
                    numeroSumasMedia = 0;
                    atentadosTotales = 0;
                    pulsacionSearch = true;
                    if(depuracion == true){
                        System.out.println("Media de valores obtenida");
                    }
                }
                // Captura de excepción por fichero no encontrado
                catch (FileNotFoundException ex) {
                    System.out.println("Error: Fichero no encontrado");
                    ex.printStackTrace();
                }
                // Captura de cualquier otra excepción
                catch(Exception ex) {
                    System.out.println("Error de lectura del fichero");
                    ex.printStackTrace();
                }
                // Asegurar el cierre del fichero en cualquier caso
                finally {
                    try {
                        // Cerrar el fichero si se ha podido abrir
                        if(br != null) {
                            br.close();
                        }
                    }
                    catch (Exception ex) {
                        System.out.println("Error al cerrar el fichero");
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        buttonExport.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent t) {
                if(pulsacionSearch == true){
                    if(depuracion == true){
                        System.out.println("Se ha pulsado el botón Exportar");
                    }
                    exportarContenido.exportarContenido(media, seleccionPais);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Exportación");
                    alert.setHeaderText("La exportación se ha realizado correctamente");
                    alert.setContentText("Se ha exportado la media de atentados que se han producido a lo largo de la historia en "+seleccionPais);
                    alert.showAndWait();
                    if(depuracion == true){
                        System.out.println("Se ha realizado la exportación de datos");
                    }
                }else{
                    if(depuracion == true){
                        System.out.println("Llamando Alert de 'Exportación fallida'");
                    }
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Exportación");
                    alert.setHeaderText("La exportación ha fallado");
                    alert.setContentText("Debes ralizar una búsqueda para realizar la exportación de datos");
                    alert.showAndWait();
                }
                
            }
        });
        
    }
}
