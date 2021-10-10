package jdk8;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author yinchao
 * @date 2020/9/24 13:45
 */
class Solution {
    public int scheduleCourse(int[][] courses) {
        int time = 0;
        int num = 0;
        Arrays.sort(courses, Comparator.comparingInt(o->o[1]-o[0]));
        Arrays.sort(courses,Comparator.comparingInt(o->o[1]-o[0]).thenComparingInt(o->o[0]));
        for(int i=0;i<courses.length;i++){
            System.out.println(courses[i][0]+" "+courses[i][1]);
            if(time+courses[i][0]<courses[i][1]){
                time += courses[i][0];
                num++;
            }
        }
        return num;
    }
}

public class Test {

    public static void main (String[] args) {
    }
}
