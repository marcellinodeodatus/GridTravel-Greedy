import java.io.*;
import java.util.*;

public class GridTraversal {
    static char[][] grid;
    static boolean[][] visited;
    static int n;
    static int startX, startY;
    static final int[] dx = {-1, 1, 0, 0};
    static final int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("grid.in"));
        int k = Integer.parseInt(br.readLine());

        for (int gridNumber = 1; gridNumber <= k; gridNumber++) {
            n = Integer.parseInt(br.readLine());
            grid = new char[n][n];
            visited = new boolean[n][n];

            // Read the grid
            for (int i = 0; i < n; i++) {
                grid[i] = br.readLine().toCharArray();
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 'X') {
                        startX = i;
                        startY = j;
                    }
                }
            }

            // Start the recursive exploration
            dfs(startX, startY);

            // Output the result for the current grid
            System.out.println("Reachable Map #" + gridNumber);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (visited[i][j] && grid[i][j] != 'X') {
                        System.out.print('R');
                    } else {
                        System.out.print(grid[i][j]);
                    }
                }
                System.out.println();
            }

            if (gridNumber < k) {
                System.out.println();
            }
        }

        br.close();
    }

    // Recursive DFS to explore all reachable locations
    private static void dfs(int x, int y) {
        visited[x][y] = true;

        char current = grid[x][y];

        // Case: If it's a punctuation symbol, we can jump to any other same symbol
        if (isPunctuation(current)) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == current && !visited[i][j]) {
                        dfs(i, j);
                    }
                }
            }
        }

        // Explore neighbors (up, down, left, right)
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (isValid(newX, newY) && !visited[newX][newY]) {
                char next = grid[newX][newY];
                // Handle movement based on current symbol
                if (Character.isDigit(current)) {
                    if (isPunctuation(next) || (Character.isDigit(next) && next >= current)) {
                        dfs(newX, newY);
                    }
                } else if (isPunctuation(current)) {
                    if (next != 'W') {
                        dfs(newX, newY);
                    }
                }
            }
        }
    }

    // Check if a symbol is punctuation
    private static boolean isPunctuation(char c) {
        return c == '.' || c == '?' || c == '!';
    }

    // Check if a position is valid (within bounds and not a wall)
    private static boolean isValid(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n && grid[x][y] != 'W';
    }
}
