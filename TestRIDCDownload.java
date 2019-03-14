package project1;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.Stack;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.model.serialize.HdaBinderSerializer;
import oracle.stellent.ridc.protocol.ServiceResponse;

@WebServlet(name = "TestRIDCDownload", urlPatterns = { "/TestRIDCDownload" })
public class TestRIDCDownload extends HttpServlet {
   
    @SuppressWarnings("compatibility:-5262082555196785075")
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252; application/pdf; xml";
       // File path = new File("D:\\RIDC\\test22.txt");
     String format;
       String name;
      // String myFileToEdit = null;
                        String ffilename;
            public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                  IdcClientManager manager = new IdcClientManager ();
                System.out.println("inside main class");
               
                 try{
                  // Create a new IdcClient Connection using idc protocol (i.e. socket connection to Content Server)
                  IdcClient idcClient = manager.createClient ("idc://192.168.0.215:4448");
                  // Create new context using the 'sysadmin' user
                  IdcContext userContext = new IdcContext ("oudadmin");
                     System.out.println("inside doget oudadmin");
                  // Create an HdaBinderSerializer; this is not necessary, but it allows us to serialize the request and response data binders
                  HdaBinderSerializer serializer = new HdaBinderSerializer ("UTF-8", idcClient.getDataFactory ());

                  // Create a new binder for retrieving the document information for a content item revision
                  DataBinder dataBinder = idcClient.createBinder();
                     System.out.println("inside doget dataBinder");
                  dataBinder.putLocal("IdcService", "DOC_INFO");
                  dataBinder.putLocal("dID","7406");
                     dataBinder.putLocal("FileRenditionsInfo", "true");

                  // Write the data binder for the request to stdout
                  serializer.serializeBinder (System.out, dataBinder);
                     System.out.println("inside doget out.println.databinder");

                  // Send the request to Content Server
                  ServiceResponse response1 = idcClient.sendRequest(userContext,dataBinder);

                  // Get the data binder for the response from Content Server
                  DataBinder responseData = response1.getResponseAsBinder();
                  // Write the response data binder to stdout
                  serializer.serializeBinder (System.out, responseData);

                  // Retrieve the DOC_INFO ResultSet from the response
                  DataResultSet resultSet = responseData.getResultSet("DOC_INFO");
                     String DocUrl = responseData.getLocal("DocUrl");
                     System.out.println("the docurl is "+ DocUrl);
                format = responseData.getLocal("dFormat");
                     System.out.println("format"+format);
                     ffilename= responseData.getLocal("fFileName");
                     System.out.println("println filename"+ffilename);
                     name= responseData.getLocal("dDocName");
                     System.out.println("inside doget output the resultset---------------------------------");
                     
                } catch (IdcClientException ice){
                  ice.printStackTrace();
                }
                try
                {
                    System.out.println("inside idcclient");
                     // Create a new IdcClient Connection using idc protocol (i.e. socket connection to Content Server)
                     IdcClient idcClient = manager.createClient ("idc://192.168.0.215:4448");
                     // Create new context using the 'sysadmin' user
                     IdcContext userContext = new IdcContext ("oudadmin");
                     // Create an HdaBinderSerializer; this is not necessary, but it allows us to serialize the request and response data binders
                     HdaBinderSerializer serializer = new HdaBinderSerializer ("UTF-8", idcClient.getDataFactory ());
                
                     // Databinder for checkin request
                     DataBinder dataBinder = idcClient.createBinder();
                   System.out.println("inside dataBinder and above GET_FILE");
                    dataBinder.putLocal("IdcService", "GET_FILE");
                    //dID is important ----------------
                    System.out.println("after GET_FILE");
                    dataBinder.putLocal("dID","7406" );
                  // dataBinder.putLocal("dDocName","WCC215.GENX.COM004803");
                    System.out.println("after did and ddocname");
                   //  dataBinder.putLocal("dDocName", "MYDOCNAME");
                   // dataBinder.putLocal("allowInterrupt", "1");

                    serializer.serializeBinder (System.out, dataBinder);
                    // Send the request to Content Server
                    System.out.println("after did and after serializaiton");
                    // Create an output stream to output the file received
                      
                    System.out.println("fos");
                       // Send the request to Content Server
                       ServiceResponse response1 =
                       idcClient.sendRequest(userContext, dataBinder);
                    InputStream fis = response1.getResponseStream();
                                       System.out.println("fos222222222222222222222222222222222222222");
                                      // FileOutputStream fos = new FileOutputStream(path);
                                      String home = System.getProperty("user.home");
                                   // FileOutputStream fos = new FileOutputStream("d://RIDC//"+ffilename);
                                   FileOutputStream fos = new FileOutputStream(home+"//Downloads//"+ffilename);
                                       System.out.println("after outputstream" +fos);

                                          // Read the data in 1KB chunks and write it to the file
                                          byte[] readData = new byte[4096];
                                          int i = fis.read(readData);
                                       System.out.println("hellllllllllllllllllllllllllllllll inside byte");

                                          while (i != -1) {
                                            fos.write(readData, 0, i);
                                              System.out.println("reading the data ");
                                            i = fis.read(readData);
                                          }
                                        System.out.println("after while test");
                                          // Close the socket connection
                                          response1.close();
                                          // Don't leave the streams open
                                          fos.close();
                                          fis.close();
                                   }                                      
                                       catch (IdcClientException ice)
                                    {
                                      ice.printStackTrace();
                                    }
                                     catch (IOException ioe)
                                     {
                                    ioe.printStackTrace();
                                   }
                                response.sendRedirect("donwload.jsp");
                               }
                    }
