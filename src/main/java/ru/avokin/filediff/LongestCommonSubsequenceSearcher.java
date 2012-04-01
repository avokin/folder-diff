package ru.avokin.filediff;

import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;
import ru.avokin.uidiff.diff.folderdiff.model.DiffStatusEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 24.08.2010
 */
public class LongestCommonSubsequenceSearcher {

    public static int[][] findLongestCommonSubsequence(List<String> l1, List<String> l2) {
        int[][] max = new int[l1.size()][l2.size()];

        //noinspection ConditionalExpression
        max[0][0] = (l1.get(0).equals(l2.get(0))) ? 1 : 0;

        // First horizontal line.
        for (int i = 1; i < l1.size(); i++) {
            if (l1.get(i).equals(l2.get(0))) {
                max[i][0] = 1;
            } else {
                max[i][0] = max[i-1][0];
            }
        }

        // First vertical line.
        for (int j = 1; j < l2.size(); j++) {
            if (l2.get(j).equals(l1.get(0))) {
                max[0][j] = 1;
            } else {
                max[0][j] = max[0][j - 1];
            }
        }

        for (int i = 1; i < l1.size(); i++) {
            for (int j = 1; j < l2.size(); j++) {
                //noinspection ConditionalExpression
                int equals = l1.get(i).equals(l2.get(j)) ? 1 : 0;
                max[i][j] = Math.max(max[i][j - 1], max[i - 1][j]);
                max[i][j] = Math.max(max[i][j], max[i - 1][j - 1] + equals);
            }
        }

        return max;
    }

    public static Difference createDifference(int leftStartLine, int leftEndLine, int rightStartLine, int rightEndLine) {
        DiffStatusEnum status = DiffStatusEnum.changed;

        if (leftStartLine > leftEndLine) {
            status = DiffStatusEnum.added;
        }
        if (rightStartLine > rightEndLine) {
            status = DiffStatusEnum.deleted;
        }

        return new Difference(new FileDiffCodeBlock(leftStartLine, leftEndLine, status),
                new FileDiffCodeBlock(rightStartLine, rightEndLine, status));
    }

    public static FileDifference diff(List<String> l1, List<String> l2) {
        int[][] maxSubsequincesLength = findLongestCommonSubsequence(l1, l2);

        // Looking for last common item.
        int maxI = 0;
        int maxJ = 0;
        for (int i = 0; i < maxSubsequincesLength.length; i++) {
            for (int j = 0; j < maxSubsequincesLength[0].length; j++) {
                if (l1.get(i).equals(l2.get(j)) && maxSubsequincesLength[maxI][maxJ] < maxSubsequincesLength[i][j]) {
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        List<Difference> result = new ArrayList<Difference>();

        if (maxI < maxSubsequincesLength.length - 1 || maxJ < maxSubsequincesLength[0].length - 1) {
            // Tail change or insert.
            int leftTop = maxI + 1;
            int leftBottom = maxSubsequincesLength.length - 1;

            int rightTop = maxJ + 1;
            int rightBottom = maxSubsequincesLength[0].length - 1;

            result.add(createDifference(leftTop, leftBottom, rightTop, rightBottom));
        }

        boolean diffBlock = false;

        int bottomI = -1;
        int bottomJ = -1;

        int i = maxI;
        int j = maxJ;
        while (i >= 0 || j >= 0) {
            if (i >= 0 && j >= 0 && l1.get(i).equals(l2.get(j))) {
                if (diffBlock) {
                    // Diff block finished.
                    //noinspection SuspiciousNameCombination
                    result.add(createDifference(i + 1, bottomI, j + 1, bottomJ));
                }
                diffBlock = false;
                i--;
                j--;
            } else {
                if (!diffBlock) {
                    bottomI = i;
                    bottomJ = j;
                    diffBlock = true;
                }

                if (i == -1) {
                    j--;
                } else if (j == -1) {
                    i--;
                } else if (i > 0 && j > 0) {
                    if (maxSubsequincesLength[i - 1][j] > maxSubsequincesLength[i][j - 1]) {
                        i--;
                    } else {
                        j--;
                    }
                } else if (i == 0) {
                    j--;
                } else {
                    i--;
                }
            }
        }

        if (diffBlock) {
            // Leading change or insert.
            //noinspection SuspiciousNameCombination
            result.add(createDifference(0, bottomI, 0, bottomJ));
        }

        Collections.reverse(result);

        return new FileDifference(result);
    }

}
