package swea;

import java.util.Arrays;
import java.util.Scanner;

public class Solution1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		
		for(int tc = 1; tc <= T; tc++) {
			int[] arr = new int[101];
			int N = sc.nextInt();
			for(int i = 0; i < 1000; i++) {
				int tmp = sc.nextInt();
				arr[tmp]++;
			}
			int max = 0;
			int result = 0;
			for(int i = 0 ; i < 101; i++) {
				if(arr[i] >= max) {
					max = arr[i];
					result = i;
				}
			}
			
			System.out.println("#"+tc+" "+result);
			
		}
		
	}

}
