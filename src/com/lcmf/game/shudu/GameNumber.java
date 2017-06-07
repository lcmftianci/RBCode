package com.lcmf.game.shudu;

public class GameNumber {
	//���һ�������֣�0����ո񡣡�
	private final String str = 
			 "360000000004230800000004200"
			+"070460003820000014500013020"
			+"001900000007048300000000045";
	private   int sudoku[] = new int[9*9];
	//����ù������ݲ����ظ�
	private   int used[][][]= new int[9][9][];
	public GameNumber()
	{
		sudoku = fromPuzzleString(str);
		calculateAllUsedTiles();
	}
	//���ݾŹ������꣬��д��Ӧ�����֡�
		private  int getTile(int x,int y)
		{
			return sudoku[y*9+x];
		}
	//��ʼ������
	private int[] fromPuzzleString(String src) {
		// TODO �Զ����ɵķ������
		int[] sudo = new int[str.length()];
		for (int i =0; i<sudo.length;i++){
			sudo[i]= str.charAt(i)-'0';
		}
		return sudo;
	}
	
	public String getTileString(int x, int y){
		int v =getTile(x, y);
		if(v==0){
			return"";
		}
		else {
			return String.valueOf(v);
		}
	}
	//���ڼ������е�Ԫ���Ӧ�Ĳ����õ�����
	public void calculateAllUsedTiles(){
		for(int x=0;x<9;x++)
		{
			for(int y=0;y<9;y++){
				used[x][y]= calculateUsedTiles(x,y);
			}
		}
	}
	//ȥ��ĳһ��Ԫ���и������õ�����
	public  int[] getUsedTilesByCoor(int x,int y){
		return used[x][y];
	}
	
	private   int[] calculateUsedTiles(int x, int y) {
		// TODO �Զ����ɵķ������
		int c[]=new int[9];
		for(int i=0;i<9;i++){
			if(i==y)
				continue;
			
			int t= getTile(x, i);
			if(t!=0)
				c[t-1]=t;
		}
		for(int i=0;i<9;i++){
			if(i==x)
				continue;
		
			int t= getTile(i, y);
			if(t!=0)
				c[t-1]=t;
		}
		int startx = (x/3)*3;
		int starty = (y/3)*3;
		for (int i =startx ;i<startx+3;i++){
			for(int j=starty;j<starty+3;j++){
				if(i==x&&j==y)
					continue;
				int t =getTile(i, j);
				if(t!=0)
					c[t-1]=t;
			}
		}
		//ѹ���ռ�
		int nused = 0;
		for(int t:c){
			if(t!=0)
				nused++;
		}
		int[] c1=new int [nused];
		nused=0;
		for(int t:c){
			if(t!=0)
				c1[nused++]=t;
			
		}
		return c1;
	}
	protected  boolean setTileIfValid(int x, int y,int value) {
		int tiles[] = getUsedTilesByCoor(x, y);
		if(value!=0){
			for(int tile:tiles){
				if(tile==value)
					return false;
			}
		}
		setTile(x, y, value);
		calculateAllUsedTiles();
		return true;
	}
	private  void setTile(int x, int y, int value) {
		// TODO �Զ����ɵķ������
		sudoku[y*9+x]=value;
		
	}
	private int[] getUsedTiles(int x, int y) {
		// TODO �Զ����ɵķ������
	
		return used[x][y];
	}
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO �Զ����ɵķ������
//
//	}
}
