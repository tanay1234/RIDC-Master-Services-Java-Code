package ridc;


import java.io.*;
import oracle.stellent.ridc.*;
import oracle.stellent.ridc.model.*;
import oracle.stellent.ridc.protocol.*;
import oracle.stellent.ridc.model.serialize.*;
/*
* This is a class used to test the basic functionality
* of submitting a checkin to Content Server using RIDC.
*/
public class UploadContentRIDC {
public static void main(String[] args) {
  // Create a new IdcClientManager
  IdcClientManager manager = new IdcClientManager ();
  try{
    // Create a new IdcClient Connection using idc protocol (i.e. socket connection to Content Server)
    IdcClient idcClient = manager.createClient ("idc://192.168.0.215:4448");
    // Create new context using the 'sysadmin' user
    IdcContext userContext = new IdcContext ("oudadmin");
    // Create an HdaBinderSerializer; this is not necessary, but it allows us to serialize the request and response data binders
    HdaBinderSerializer serializer = new HdaBinderSerializer ("UTF-8", idcClient.getDataFactory ());

    // Databinder for checkin request
    DataBinder dataBinder = idcClient.createBinder();
    dataBinder.putLocal("IdcService", "CHECKIN_UNIVERSAL");
    //dataBinder.putLocal("dDocName","TestRIDCCheckin");
    dataBinder.putLocal("dDocTitle", "NEW Checkin RIDC ");
    dataBinder.putLocal("dDocType", "Document");
    dataBinder.putLocal("dDocAccount", "");
    dataBinder.putLocal("dSecurityGroup", "Public");
      dataBinder.putLocal("dDocName","new");
    //dataBinder.putLocal("fParentGUID",  "E23C90BC6FE043A0889C2CCF1E641258");
    dataBinder.addFile("primaryFile", new File("d://test.txt"));
   

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