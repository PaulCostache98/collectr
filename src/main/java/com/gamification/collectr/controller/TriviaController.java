package com.gamification.collectr.controller;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.games.Question;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Controller
public class TriviaController {

    List<String> answers = new ArrayList<>();
    String correctAnswer;

    int count=1;

    int correctCount=0;

    @Autowired
    UserService userService;


    @RequestMapping("games/1")
    String trivia(Model model) {

//        if(count == 5) {
//            count = 0;
//            return "redirect:/games/1/end";
//        }
        List<Question> questions = readDataLineByLine("src/main/resources/trivia.csv");
        Random random = new Random();
        int questionNumber = random.nextInt(198);
        correctAnswer = questions.get(questionNumber).getAnswer();
        answers.clear();
        answers.add(correctAnswer);
        answers.add(questions.get(random.nextInt(198)).getAnswer());
        answers.add(questions.get(random.nextInt(198)).getAnswer());
        Collections.shuffle(answers);
        model.addAttribute("question", questions.get(questionNumber));
        model.addAttribute("answers", answers);
//        count++;
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("count", count);
        return "trivia";
    }

    @RequestMapping("games/1/answer/{number}")
    String triviaAnswer(@PathVariable Integer number, Model model) {

        if(count == 5) {
            count = 1;
            return "redirect:/games/1/end";
        }

        if(Objects.equals(answers.get(number), correctAnswer)) {
            correctCount++;
        }

        List<Question> questions = readDataLineByLine("src/main/resources/trivia.csv");
        Random random = new Random();
        int questionNumber = random.nextInt(198);
        correctAnswer = questions.get(questionNumber).getAnswer();
        answers.clear();
        answers.add(correctAnswer);
        answers.add(questions.get(random.nextInt(198)).getAnswer());
        answers.add(questions.get(random.nextInt(198)).getAnswer());
        Collections.shuffle(answers);
        model.addAttribute("question", questions.get(questionNumber));
        model.addAttribute("answers", answers);
        count++;
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("count", count);
        return "trivia";
    }

    @RequestMapping("/games/1/end")
    String triviaEnd(Model model) {
        model.addAttribute("correctCount", correctCount);
        if(correctCount >= 3) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUser user = userService.findUserByUserName(authentication.getName());
            if(!user.getQuests().stream().filter(q -> Objects.equals(q.getQuestType(), "Trivia") && !(!(q.getCompleted() == null) && q.getCompleted().contains(user))).toList().isEmpty())
            {
                List<Quest> questsTemp = user.getQuests().stream().filter(q -> Objects.equals(q.getQuestType(), "Trivia") && !(!(q.getCompleted() == null) && q.getCompleted().contains(user))).toList();
                questsTemp.forEach(user.getQuests()::remove);
                List<List<Integer>> stepsTemp = questsTemp.stream().map(Quest::getSteps).toList();
                questsTemp.forEach(q -> q.getSteps().set(q.getUsers().indexOf(user), q.getSteps().get(q.getUsers().indexOf(user))-1));
                for (Quest q: questsTemp) {
                    if(q.getSteps().get(q.getUsers().indexOf(user)) <= 0)
                    {
                        q.getSteps().set(q.getUsers().indexOf(user), 0);
                        q.getCompleted().add(user);

                    }
                }
                user.getQuests().addAll(questsTemp);
                userService.saveUser(user);
            }
        }
        correctCount=0;
        return "trivia-end";
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


