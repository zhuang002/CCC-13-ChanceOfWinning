/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chanceofwinning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class ChanceOfWinning {

    /**
     * @param args the command line arguments
     */
    static int favoriteTeam;
    static int[] matches={12,13,14,23,24,34};
    static int[] initScores={0,0,0,0};
    static Integer[] leftMatches=null;
    
    public static void main(String[] args) {
        // TODO code application logic here
        HashSet<Integer> matchSet=new HashSet();
        for (int m:matches) matchSet.add(m);
        Scanner sc=new Scanner(System.in);
        favoriteTeam=sc.nextInt();
        int n=sc.nextInt();
        for (int i=0;i<n;i++) {
            int team1=sc.nextInt();
            int team2=sc.nextInt();
            int match=makeMatch(team1,team2);
            matchSet.remove(match);
            int score1=sc.nextInt();
            int score2=sc.nextInt();
            if (score1>score2) initScores[team1-1]+=3;
            else if (score1==score2) {
                initScores[team1-1]+=1;
                initScores[team2-1]+=1;
            } else initScores[team2-1]+=3;
        }

        ArrayList<int[]> scoresList=new ArrayList();
        scoresList.add(initScores);
        ArrayList<int[]> newScoresList=new ArrayList();
        for (int match : matchSet) {
            for (int[] scores:scoresList) {
                int[] scores1=getScoreWithMatch(1,scores,match);
                int[] scores2=getScoreWithMatch(0,scores,match);
                int[] scores3=getScoreWithMatch(-1,scores,match);
                newScoresList.add(scores1);
                newScoresList.add(scores2);
                newScoresList.add(scores3);
            }
            scoresList=newScoresList;
            newScoresList=new ArrayList();
        }
        
        int count=0;
        for (int[] scores:scoresList) {
            if (isChampion(favoriteTeam,scores)) {
                count++;
            }
        }
        System.out.println(count);
        
    }

    private static int[] getScoreWithMatch(int result, int[] passedScores, int match) {
        int[] scores=passedScores.clone();
        int team2=match%10;
        int team1=match/10;
        
        switch (result) {
            case 1:
                scores[team1-1]+=3;
                break;
            case 0:
                scores[team1-1]++;
                scores[team2-1]++;
                break;
            default:
                scores[team2-1]+=3;
                break;
        }
        return scores;
    }

    private static boolean isChampion(int favoriteTeam, int[] scores) {
        int max=0;
        boolean peer=false;
        for (int score:scores) {
            if (score>max) {
                max=score;
                peer=false;
            }
            else if (score==max && score!=0) {
                peer=true;
            }
                
        }
        return scores[favoriteTeam-1]==max && !peer;
    }

    private static int makeMatch(int team1, int team2) {
        return (team1<team2)?team1*10+team2:team2*10+team1;
    }
    
}
