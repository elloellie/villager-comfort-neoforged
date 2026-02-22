package net.elleoellie.villagercomfort.core.math;


public class MathHelper
{
    public static int scale(int value, int prevScale, int newScale)
    {
        return (int)(((float)value / (float)prevScale) * newScale);
    }

    public static int interpolate(int point, int minPoint, int maxPoint, int minValue, int maxValue) {
        int avgValue = (int) ((minValue + maxValue) / 2);

        if (maxPoint < minPoint) {
            int swap = minPoint;
            minPoint = maxPoint;
            maxPoint = swap;

            swap = minValue;
            minValue = maxValue;
            maxValue = swap;
        }

        if (point >= maxPoint) {
            return maxValue;
        } else if (point <= minPoint) {
            return minValue;
        } else {
            int pointDistance = maxPoint - minPoint;
            int valueDistance = maxValue - minValue;

            if (pointDistance == 0) {
                return avgValue;
            } else {
                int value = minValue + (int) ((float) ((point - minPoint) * valueDistance) / pointDistance);
                return value;
            }
        }
    }
}
