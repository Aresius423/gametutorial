package models;

public class RawModel {
	private int vaoID;
	private int vertexNum;
	
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexNum() {
		return vertexNum;
	}	
	
	public RawModel(int ID, int num)
	{
		this.vaoID = ID;
		this.vertexNum = num;
	}
}
