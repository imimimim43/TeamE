import java.util.Arrays;
import java.util.Scanner;
public class Main {
	static int[] R;
	static int[] C;
	static int[][] dp;
	static int N;
	
	public static int func(int x,int y) {
		int mm= 2147483647; // max값을 넣기
		if(x==y) {
			return 0; 	
		}
		if(dp[x][y]!=0) {
			return dp[x][y];
		}
		for(int k=x;k<y;k++) {
			mm=Math.min(mm, func(x,k)+func(k+1,y)+R[x]*C[k]*C[y]);
		}
		return dp[x][y]=mm;
	}
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		N=sc.nextInt();   //행렬의 수 입력 받기
		R=new int[500];
		C=new int[500];
		dp=new int[500][500];
		
		for(int i=0;i<N;i++) {
			R[i]=sc.nextInt();
			C[i]=sc.nextInt();
		}
		
		func(0,N-1);
		System.out.println(dp[0][N-1]);
		
	}
}
