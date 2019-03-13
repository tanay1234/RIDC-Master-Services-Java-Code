package ridc;
/**
 * @author : Tanay Sinha
 * @version: Delete_Doc @10.1
 * @Date   : 12/3
 */
import java.io.IOException;
import oracle.stellent.ridc.*;
import oracle.stellent.ridc.model.*;
import oracle.stellent.ridc.model.serialize.HdaBinderSerializer;
import oracle.stellent.ridc.protocol.*;

public class DeleteContentRIDC {
 public static void main(String[] args){
      
        IdcClientManager manager = new IdcClientManager ();
        
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
          dataBinder.putLocal("IdcService", "DELETE_DOC");
          dataBinder.putLocal("dID","8403");
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

          // Function completed !!
             
         } catch (IdcClientException ice){
           ice.printStackTrace();
         } catch (IOException ioe){
           ioe.printStackTrace();
        }
}
}
