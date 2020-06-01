package controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("images")
public class ImageController {
    @GET
    @Path("/{imageName}")
    @Produces("image/jpg")
    public Response getFile(@PathParam("imageName") String imageName) {
        File dataDir = new File(System.getProperty("jboss.server.data.dir"));
        File file = new File(dataDir, "images/"+ imageName +".jpg");
        return Response.ok(file,"image/jpg").header("Inline","filename=\""+file.getName()+"\"").build();
    }
}
