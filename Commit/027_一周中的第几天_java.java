class Solution {
    private static final String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public String dayOfTheWeek(int day, int month, int year) {
        // 蔡勒公式
        if(month <= 2) {
            month += 12;
            year--;
        }
        int quotient = year / 100;
        year = year % 100;
        return week[((Math.round(quotient / 4) - 2 * quotient + year + Math.round(year / 4) + Math.round((13 * (month + 1)) / 5) + day - 1) % 7 + 7) % 7];
    }
}