package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import oracle.stellent.ridc.*;
import oracle.stellent.ridc.model.*;
import oracle.stellent.ridc.protocol.*;
import oracle.stellent.ridc.protocol.intradoc.*;
import oracle.stellent.ridc.common.log.*;
import oracle.stellent.ridc.model.serialize.*;

@WebServlet(name = "TestRIDCDocInfo", urlPatterns = { "/TestRIDCDocInfo" })
public class TestRIDCDocInfo extends HttpServlet {
    @SuppressWarnings("compatibility:1258838660011599168")
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        IdcClientManager manager = new IdcClientManager ();
        System.out.println("inside doget funtion");
         try{
          // Create a new IdcClient Connection using idc protocol (i.e. socket connection to Content Server)
          IdcClient idcClient = manager.createClient ("idc://192.168.0.215:4448");
          // Create new context using the 'sysadmin' user
          IdcContext userContext = new IdcContext ("weblogic");
             System.out.println("inside doget oudadmin");
          // Create an HdaBinderSerializer; this is not necessary, but it allows us to serialize the request and response data binders
          HdaBinderSerializer serializer = new HdaBinderSerializer ("UTF-8", idcClient.getDataFactory ());

          // Create a new binder for retrieving the document information for a content item revision
          DataBinder dataBinder = idcClient.createBinder();
             System.out.println("inside doget dataBinder");
          dataBinder.putLocal("IdcService", "DOC_INFO");
          dataBinder.putLocal("dID","2006");
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
             System.out.println("inside doget output the resultset");
          // Iterate over the ResultSet, retrieve properties from the content item (should only be one row)
          for (DataObject dataObject : resultSet.getRows ()) {
          System.out.println ("Title is: " + dataObject.get ("dDocTitle"));
           System.out.println ("Author is: " + dataObject.get ("dDocAuthor"));
             System.out.println ("Format is: " + dataObject.get ("dFormat"));
             System.out.println ("URL is: " + dataObject.get ("DocUrl"));
             System.out.println ("location is: " + dataObject.get ("dLocation"));
             System.out.println ("filesize is: " + dataObject.get ("dFileSize"));
             System.out.println ("DocName is: " + dataObject.get ("dDocName"));
             System.out.println ("Security Group is: " + dataObject.get (" dSecurityGroup"));
            
                
              
         }

         } catch (IdcClientException ice){
           ice.printStackTrace();
         } catch (IOException ioe){
           ioe.printStackTrace();
        }
        response.sendRedirect("donwload.jsp");
    }

}
