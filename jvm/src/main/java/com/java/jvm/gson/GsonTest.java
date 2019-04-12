package com.java.jvm.gson;

import java.util.List;

/**
 * 用GSON解析Json数组
 */
public class GsonTest {
    public static void main(String[] args) {
        // Json数组最外层要加"[]"
        String jsonData = "[{'name':'John', 'grade':[{'course':'English','score':100},{'course':'Math','score':78}]},{'name':'Tom', 'grade':[{'course':'English','score':86},{'course':'Math','score':90}]}]";

        List<Student> students = GsonUtil.parseJsonArrayWithGson(jsonData,
                Student.class);
        System.out.println(students);
    }
}

class Student {
    private String name;
    private List<Grade> grade; // 因为grade是个数组，所以要定义成List

    public class Grade {
        private String course;
        private String score;

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Grade> getGrade() {
        return grade;
    }

    public void setGrade(List<Grade> grade) {
        this.grade = grade;
    }

}