package ridc;
import java.io.File;
import java.io.IOException;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.serialize.HdaBinderSerializer;
import oracle.stellent.ridc.protocol.ServiceResponse;
public class CreateContentFolderRIDC {
    public static void main(String[] args) {
    IdcClientManager manager = new IdcClientManager ();
    try{
    IdcClient idcClient = manager.createClient ("idc://192.168.0.215:4448");
    // Create new context using the 'sysadmin' user
    IdcContext userContext = new IdcContext ("oudadmin");
    // Create an HdaBinderSerializer; this is not necessary, but it allows us to serialize the request and response data binders
    HdaBinderSerializer serializer = new HdaBinderSerializer ("UTF-8", idcClient.getDataFactory ());

    // Databinder for checkin request
    DataBinder dataBinder = idcClient.createBinder();
    dataBinder.putLocal("IdcService", "CREATE_FOLDER");
    //dataBinder.putLocal("dDocName","TestRIDCCheckin");
    dataBinder.putLocal("dFolderName","TANAYDEMO_FOLDER");
    dataBinder.putLocal("dFolderID","007");
    // dataBinder.putLocal("dFolderDescription","<description for the folder>");
    dataBinder.putLocal("dDocAuthor","tanay");
    dataBinder.putLocal("dSecurityGroup","Public"); 
    //dataBinder.putLocal("dCategoryID","<under which RC this folder is to be created>");
    //dataBinder.putLocal("dIsVital","0"); // Yes =1 , No=0

    // Write the data binder for the request to stdout
    serializer.serializeBinder (System.out, dataBinder);
    // Send the request to Content Server
    ServiceResponse response = idcClient.sendRequest(userContext,dataBinder);
    // Get the data binder for the response from Content Server
    DataBinder responseData = response.getResponseAsBinder();
    // Write the response data binder to stdout
    serializer.serializeBinder (System.out, responseData);


    } catch (IdcClientException ice){
    ice.printStackTrace();
    } catch (IOException ioe){
    ioe.printStackTrace();
    }
    }
    
}
