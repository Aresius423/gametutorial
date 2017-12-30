package importer;

import java.util.List;

public class FaceData {
	public int pos1;
	public int pos2;
	public int pos3;
	public int tex1;
	public int tex2;
	public int tex3;
	public int nor1;
	public int nor2;
	public int nor3;
	
	public FaceData(int pos1, int pos2, int pos3, int tex1, int tex2, int tex3, int nor1, int nor2, int nor3) {
		super();
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.pos3 = pos3;
		this.tex1 = tex1;
		this.tex2 = tex2;
		this.tex3 = tex3;
		this.nor1 = nor1;
		this.nor2 = nor2;
		this.nor3 = nor3;
	}
	
	public FaceData(List<Integer> ints)
	{
		this.pos1 = ints.get(0);
		this.pos2 = ints.get(3);
		this.pos3 = ints.get(6);
		
		this.tex1 = ints.get(1);
		this.tex2 = ints.get(4);
		this.tex3 = ints.get(7);
		
		this.nor1 = ints.get(2);
		this.nor2 = ints.get(5);
		this.nor3 = ints.get(8);
	}		
}
