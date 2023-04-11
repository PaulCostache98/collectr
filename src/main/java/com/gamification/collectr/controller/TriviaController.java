package com.gamification.collectr.controller;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.service.BadgeService;
import com.gamification.collectr.games.Question;
import com.gamification.collectr.service.QuestService;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.util.*;

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

    @Autowired
    QuestService questService;

    @Autowired
    BadgeService badgeService;


    @RequestMapping("games/1")
    String trivia(Model model) {
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
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("count", count);
        return "trivia";
    }

    @RequestMapping("games/1/answer/{number}")
    String triviaAnswer(@PathVariable Integer number, Model model) {

        if(count == 5) {
            count = 1;
            if(Objects.equals(answers.get(number), correctAnswer)) {
                correctCount++;
            }
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
            List<Quest> quests = questService.findAll().stream().filter(q -> q.getQuestType().equals("Trivia") && !q.getCompleted().contains(user.getId()) && user.getQuests().contains(q)).toList();
            quests = quests.stream().filter(q -> !q.getCompleted().contains(user.getId())).toList();
            if(!quests.isEmpty())
            {
                quests.forEach(user.getQuests()::remove);
                quests.forEach(q -> q.getSteps().set(userService.findAll().indexOf(user), q.getSteps().get(userService.findAll().indexOf(user))-1));
                for (Quest q: quests) {
                    if(q.getSteps().get(userService.findAll().indexOf(user)) == 0)
                    {
                        q.getSteps().set(userService.findAll().indexOf(user), -1);
                        q.getCompleted().add(user.getId());
                        user.setUserTokens(user.getUserTokens()+q.getReward());
                        questService.saveQuest(q);
                        List<Badge> badgeTemp = badgeService.findAll().stream().filter(b -> (b.getGame() != null && b.getGame().getType().equals(q.getQuestType()))).toList();
                        badgeTemp.forEach(b -> b.getSteps().set(userService.findAll().indexOf(user), b.getSteps().get(userService.findAll().indexOf(user))+1));
                        List<Badge> badgeCompleted = badgeTemp.stream().filter(b -> Objects.equals(b.getSteps().get(userService.findAll().indexOf(user)), b.getDefaultSteps())).toList();
                        user.getBadges().addAll(badgeCompleted);
                    }
                }
                user.getQuests().addAll(quests);
                userService.saveUser(user);
            }
            correctCount=0;
        }

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


