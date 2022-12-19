package com.example.demo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Clase para consumir servicios web
 *
 * @author marior.estrada
 */
public class ConsumeServices  {
//POST, GET
    private String methodhttp;
//"text/xml;charset=UTF-8"
//application/json
    private String ContenType;
//String xml o String Json
    private String parametro;
//Solo servicios Soap 
    private String encabezado;
//varible de coneccion
    private HttpURLConnection conexion = null;
//URL del servicio a consumir
    private URL url = null;
//Cuerpo de los metodos POST Y PUT
    private OutputStream os = null;
//Request inputStream
    private InputStream inputStream = null;
// 
    private BufferedReader br = null;

    public ConsumeServices() {
    }

    /**
     * Orden de ejecucion:1
     * Constructor con sobrecarga para cargar los datos necesarios para consumir
     * un servicio, al instanciar la clase.
     *
     * @param link
     * @param methodhttp
     * @param ContenType
     * @throws MalformedURLException
     * @throws IOException
     */
    public ConsumeServices(String link, String methodhttp, String ContenType) throws MalformedURLException, IOException {
        this.url = new URL(link);
        this.conexion = (HttpURLConnection) url.openConnection();
        this.conexion.setRequestMethod(methodhttp);
        this.conexion.setRequestProperty("Content-Type", ContenType);
        System.out.println(link);
        System.out.println(methodhttp);
        System.out.println(ContenType);
    }

    /**
     * Orden de ejecucion:1.1
     * Cargar los datos necesarios para consumir un servicio web
     *
     * @param link
     * @param methodhttp
     * @param ContenType
     * @throws IOException
     */
    public void Connection(String link, String methodhttp, String ContenType) throws IOException {
        this.url = new URL(link);
        this.conexion = (HttpURLConnection) url.openConnection();
        this.conexion.setRequestMethod(methodhttp);
        //this.conexion.setRequestProperty("Content-Type", ContenType);
        System.out.println(link);
        System.out.println(methodhttp);
        System.out.println(ContenType);
    }

    /**
     * Orden de ejecucion: opcional 1.2
     * Método para cargar encabezados a la conexión http diferentes a Content-Type 
     * @param nombreEncabezadoExtra
     * @param encabezadoExtra
     */
    public void setRequestProperty(String nombreEncabezadoExtra, String encabezadoExtra) {
        this.conexion.setRequestProperty(nombreEncabezadoExtra, encabezadoExtra);
        System.out.println("Nombre=" + nombreEncabezadoExtra + " Valor=" + encabezadoExtra);
    }

    /**
     * Orden de ejecucion: 1.3 neserio para Servicios SOAP
     * Método para cargar encabezados SOAPAction Requerido generalmente para
     * todos los metodos SOAP.
     *
     * @param encabezado
     */
    public void soapAction(String encabezado) {
        conexion.setRequestProperty("SOAPAction", encabezado);
        System.out.println(encabezado);
    }

    /**
     * Orden de ejecucion: 1.4 opcional para metodos http POST PUT y DELETE
     * Método para cargar el cuerpo del mensaje.
     * @param parametro
     * @throws IOException
     */
    public void setBody(String parametro) throws IOException {
        System.out.println(parametro);
        this.conexion.setDoOutput(true);
        this.os = this.conexion.getOutputStream();
        this.os.write(parametro.getBytes());
    }

    /**
     * Orden de ejecucion: 2 
     * Metodo para obtener el response de la peticion http en un String
     * @return
     * @throws IOException
     */
    public String getString() throws IOException {
        this.inputStream = conexion.getInputStream();
        StringBuilder sb = new StringBuilder();
        BufferedReader bffR = new BufferedReader(new InputStreamReader(inputStream));
        String read;
        while ((read = bffR.readLine()) != null) {
            sb.append(read);
        }
//        this.br = new BufferedReader(new InputStreamReader(inputStream));
//        String response = this.br.lines().collect(Collectors.joining());
        return sb.toString();
    }

    /**
     * Orden de ejecucion: 2.1
     * Metodo para obtener el response de la peticion http en un NodeList
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public NodeList getNodeList() throws IOException, SAXException, ParserConfigurationException {
        this.inputStream = conexion.getInputStream();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String read;
        while ((read = br.readLine()) != null) {
            sb.append(read);
        }
          try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder;
                dBuilder = dbFactory.newDocumentBuilder();

 

                InputSource is = new InputSource(new StringReader(sb.toString()));
                Document doc = dBuilder.parse(is);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("*");

 

                
                return nList;

 

            } catch (ParserConfigurationException | SAXException ex) {
                System.out.println("Consumption SOAP error:" + ex.getMessage());
                return null;
            }
}

    /**
     * Orden de ejecucion: 2.2
     * Metodo para obtener el response de la peticion http en un InputStream
     * @return
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        System.out.println(conexion.getInputStream());
        return conexion.getInputStream();
    }

    public void disconect() {
        System.out.println(this.url);
        conexion.disconnect();
    }

    public String getMethodhttp() {
        return methodhttp;
    }

    public void setMethodhttp(String methodhttp) {
        this.methodhttp = methodhttp;
    }

    public String getContenType() {
        return ContenType;
    }

    public void setContenType(String ContenType) {
        this.ContenType = ContenType;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    private Document parseXmlFile(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

