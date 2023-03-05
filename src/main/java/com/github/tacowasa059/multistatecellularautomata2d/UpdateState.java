package com.github.tacowasa059.multistatecellularautomata2d;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

public class UpdateState {
        private Cell[][] cells;
        private Location location;
        //初期化
        public UpdateState(int rows, int cols,Location location) {
            cells = new Cell[rows][cols];
            for(int i=0;i<cells.length;i++){
                for(int j=0;j<cells[i].length;j++){
                    cells[i][j]=new Cell(0);
                }
            }
            this.location=location;
            copy_from_board();
        }
        //ランダム化
        public void randomize(){
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Random random =new Random();
                    int rand= random.nextInt(3);
                    cells[i][j].setState(rand);
                }
            }
            copy_to_board();
        }
        //worldのブロックデータから設定する
        public void copy_from_board(){
            for(int i=0;i<cells.length;i++){
                for(int j=0;j<cells[i].length;j++){
                    Location loc=location.clone();
                    loc=loc.add((double)i,0,(double)j);
                    if(loc.getBlock().getType()== Material.RED_WOOL){
                        cells[i][j].setState(0);
                    }
                    else if(loc.getBlock().getType()==Material.YELLOW_WOOL){
                        cells[i][j].setState(1);
                    }
                    else if(loc.getBlock().getType()==Material.BLUE_WOOL){
                        cells[i][j].setState(2);
                    }
                    else{
                        Random random =new Random();
                        int rand= random.nextInt(3);
                        if(rand==0){
                            loc.getBlock().setType(Material.RED_WOOL);
                        }
                        else if(rand==1){
                            loc.getBlock().setType(Material.YELLOW_WOOL);
                        }
                        else{
                            loc.getBlock().setType(Material.BLUE_WOOL);
                        }
                        //System.out.println(i+" "+j+" "+rand);
                        cells[i][j].setState(rand);
                    }
                }
            }
        }
        //worldのブロックデータにコピーする
        public void copy_to_board(){
            for(int i=0;i<cells.length;i++){
                for(int j=0;j<cells[i].length;j++){
                    Location loc=location.clone();
                    loc=loc.add((double)i,0,(double)j);
                    if(cells[i][j].getState()== Cell.ROCK) loc.getBlock().setType(Material.RED_WOOL);
                    else if(cells[i][j].getState()== Cell.SCISSORS) loc.getBlock().setType(Material.YELLOW_WOOL);
                    else if(cells[i][j].getState()== Cell.PAPER) loc.getBlock().setType(Material.BLUE_WOOL);
                }
            }
        }
        //ステップ更新
        public void update() {
            Cell[][] nextGeneration = new Cell[cells.length][cells[0].length];
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell currentCell = cells[i][j];
                    int top = cells[(i - 1 + cells.length) % cells.length][j].getState();
                    int top_right = cells[(i - 1 + cells.length) % cells.length][(j + 1) % cells[i].length].getState();
                    int top_left = cells[(i - 1 + cells.length) % cells.length][(j - 1 + cells[i].length) % cells[i].length].getState();
                    int right = cells[i][(j + 1) % cells[i].length].getState();
                    int bottom = cells[(i + 1) % cells.length][j].getState();
                    int bottom_right = cells[(i + 1) % cells.length][(j + 1) % cells[i].length].getState();
                    int bottom_left = cells[(i + 1) % cells.length][(j - 1 + cells[i].length) % cells[i].length].getState();
                    int left = cells[i][(j - 1 + cells[i].length) % cells[i].length].getState();
                    List<Integer> list=Arrays.asList(top,top_left,top_right,right,left,bottom_left,bottom,bottom_right);

                    int nextState;

                    Map<Integer,Integer> map= new HashMap<>();
                    map.put(0,0);
                    map.put(1,0);
                    map.put(2,0);
                    for(int k:list) map.put(k,map.get(k)+1);
                    /*
                    int maxKey = 0;
                    int maxValue = Integer.MIN_VALUE;
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        if (entry.getValue() > maxValue) {
                            maxKey = entry.getKey();
                            maxValue = entry.getValue();
                        }
                    }
                    //int nbrState=maxKey;
                    //int nbrState = map.entrySet().stream()
                    //        .max(Map.Entry.comparingByValue())
                    //        .get()
                    //        .getKey();
                     */
                    if (currentCell.getState() == Cell.ROCK) {
                        nextState=Cell.ROCK;
                        int count=map.get(Cell.PAPER);
                        if(count>=MultiStateCellularAutomata2d.plugin.getConfig().getInt("threshold")) nextState=Cell.PAPER;
                    }
                    else if (currentCell.getState() == Cell.SCISSORS) {
                        nextState=Cell.SCISSORS;
                        int count=map.get(Cell.ROCK);
                        if(count>=MultiStateCellularAutomata2d.plugin.getConfig().getInt("threshold")) nextState=Cell.ROCK;
                    }
                    else {
                        nextState=Cell.PAPER;
                        int count=map.get(Cell.SCISSORS);
                        if(count>=MultiStateCellularAutomata2d.plugin.getConfig().getInt("threshold"))nextState=Cell.SCISSORS;
                    }
                    nextGeneration[i][j] = new Cell(nextState);
                }
            }
            cells = nextGeneration;
            copy_to_board();
        }
}
