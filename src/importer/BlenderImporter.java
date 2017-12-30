package importer;

import gameEngine.Loader;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class BlenderImporter {
	public static TexturedModel importModel(String modelName, Loader loader) {
		List<VertexData> vertexList = new ArrayList<VertexData>();
		List<Vector2f> textureList = new ArrayList<Vector2f>();
		List<Vector3f> normalList = new ArrayList<Vector3f>();
		List<Integer> indexList = new ArrayList<Integer>();
		List<FaceData> faceList = new ArrayList<FaceData>();
		
		TexturedModel texturedModel = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/" + modelName + ".obj"));

			String tmpstr;

			while ((tmpstr = reader.readLine()) != null) {
				switch (tmpstr.substring(0, 2)) {
				case "vt":
					
					textureList.add(extract2f(tmpstr));
					break;

				case "vn":

					normalList.add(extract3f(tmpstr));
					break;

				case "v ":

					vertexList.add(extractVertex(tmpstr));
					break;

				case "f ":

					faceList.add(extractFace(tmpstr));
					break;

				default:

					break;

				}
			}
			
			if(reader != null)reader.close();			
			
			for(FaceData face : faceList)
			{
				indexList.add(face.pos1-1);
				indexList.add(face.pos2-1);
				indexList.add(face.pos3-1);
				
				vertexList.get(face.pos1-1).setTex(textureList.get(face.tex1-1));
				vertexList.get(face.pos2-1).setTex(textureList.get(face.tex2-1));
				vertexList.get(face.pos3-1).setTex(textureList.get(face.tex3-1));
				
				vertexList.get(face.pos1-1).setNorm(normalList.get(face.nor1-1));
				vertexList.get(face.pos2-1).setNorm(normalList.get(face.nor2-1));
				vertexList.get(face.pos3-1).setNorm(normalList.get(face.nor3-1));
				
				//maybe don't update twice?
			}
			
			RawModel model = loader.loadToVAO(vertexList, indexList);
			ModelTexture texture = new ModelTexture(loader.loadTexture(modelName+"_texture"));
			texturedModel = new TexturedModel(model, texture);			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return texturedModel;
	}

	private static Vector3f extract3f(String str) {
		Vector3f retval;
		List<Float> vecdat = new ArrayList<Float>();
		
		for(String subst : str.split("\\s"))
		{
			if(subst.matches("[\\d\\.-]+"))
				vecdat.add(Float.parseFloat(subst));
		}
		
		assert(vecdat.size() == 3);

		retval = new Vector3f(vecdat.get(0), vecdat.get(1), vecdat.get(2));
		return retval;
	}
	
	private static Vector2f extract2f(String str) {
		Vector2f retval;
		List<Float> vecdat = new ArrayList<Float>();
		
		for(String subst : str.split("\\s"))
		{
			if(subst.matches("[\\d\\.-]+"))
				vecdat.add(Float.parseFloat(subst));
		}
		
		assert (vecdat.size() == 2);
		
		retval = new Vector2f(vecdat.get(0), vecdat.get(1));
		return retval;
	}
	
	private static VertexData extractVertex(String str) {
		VertexData retval;
		List<Float> coords = new ArrayList<Float>();
		
		for(String subst : str.split("\\s"))
		{
			if(subst.matches("[\\d\\.-]+"))
				coords.add(Float.parseFloat(subst));
		}
		
		retval = new VertexData(coords.get(0), coords.get(1), coords.get(2));

		return retval;
	}
	
	private static FaceData extractFace(String str) {
		List<Integer> ints = new ArrayList<Integer>();
		FaceData retval;
		
		for(String subst : str.split("[\\s/]"))
		{
			if(subst.matches("[\\d-]+"))
				ints.add(Integer.parseInt(subst));
		}
		
		assert (ints.size() == 9);
		
		retval = new FaceData(ints);

		return retval;
	}
}
