package com.peter.testim.api;

import com.peter.testim.bean.ImDto;

import javax.jws.WebParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 14-2-25
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */

@Path("/mainApi")
@Produces({ MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_XML })
public interface MainApi {

    @GET
    @Path("/weixin")
    String doGet(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce, @QueryParam("echostr") String echostr);

    @POST
    @Path("/weixin")
    @Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    ImDto doPost(ImDto dto);


}
