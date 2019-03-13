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

public class CloneContentRIDC {
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
    dataBinder.putLocal("IdcService", "COPY_REVISION");
    //dataBinder.putLocal("dDocName","TestRIDCCheckin");
    dataBinder.putLocal("dID","8405");
    dataBinder.putLocal("dDocName","new");
    //clone item Content ID - this is not mandatory - If set to Auto Content ID this need not be added //
    dataBinder.putLocal("newdDocName","Cloned_item");
        //In case of Framework Folder this parameter is not needed 
        dataBinder.putLocal("xCollectionID","");

        //Clone Item's Content Title 
        dataBinder.putLocal("dDocTitle","Title-cloned");

        //Any other new / custom metadata for Clone Item 
        dataBinder.putLocal("Custom","Clone Item Metadata");
    //dataBinder.putLocal("doFileCopy", "1");

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
