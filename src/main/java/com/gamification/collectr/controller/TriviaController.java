package com.gamification.collectr.controller;

import com.gamification.collectr.games.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Controller
public class TriviaController {



    @RequestMapping("games/1")
    String trivia(Model model) {

        List<Question> questions = readDataLineByLine("src/main/resources/trivia.csv");
        Random random = new Random();
        int questionNumber = random.nextInt(198);
        String firstWrongAnswer = questions.get(random.nextInt(198)).getAnswer();
        String secondWrongAnswer = questions.get(random.nextInt(198)).getAnswer();
        model.addAttribute("question", questions.get(questionNumber));
        model.addAttribute("correctAnswer", questions.get(questionNumber).getAnswer());
        model.addAttribute("firstWrongAnswer", firstWrongAnswer);
        model.addAttribute("secondWrongAnswer", secondWrongAnswer);
        return "trivia";
    }

    public static List<Question> readDataLineByLine(String fileName) {
        List<Question> questionList = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName)) {
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                Question question = new Question(nextRecord[0], nextRecord[1], nextRecord[2]);
                questionList.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionList;
    }

}


