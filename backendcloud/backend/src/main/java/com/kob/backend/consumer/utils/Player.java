package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private Integer id;
    // -1表示手动，否则表示AI
    private Integer botId;
    private String botCode;
    private Integer sx;
    private Integer sy;
    private List<Integer> steps;

    private boolean check_tail_increasing(int step) {
        if (step <= 10) {
            return true;
        }
        return step % 3 == 1;
    }
    public List<Cell> getCells() {
        List<Cell> res = new ArrayList<>();
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;

        int round = 0;
        res.add(new Cell(x, y));
        for (int d: steps) {
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y));
            if (!check_tail_increasing(++round)) {
                res.remove(0);
            }
        }
        return res;
    }

    public String stepsToString() {
        StringBuilder sb = new StringBuilder();
        for (int d: steps) {
            sb.append(d);
        }
        return sb.toString();
    }
}
