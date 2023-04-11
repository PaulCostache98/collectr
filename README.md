# collectR

This project is a simple game-based app, focused on completing quests, earning badges and simulating a marketplace between players.

## Description

collectR is based on the guidelines provided by Accesa & RaRo, namely:
In collectR players will earn tokens(presently represented as $) by completing quests, while completing enough of these quests will award them badges.
To properly simulate an economy cycle, each player will have to buy the quests they wish to complete. Quests will reward more than they cost, naturally.
SYSTEM(or Admin) created quests have a reset cycle of 24 hours. They can be purchased again after each reset, still counting as progress towards badges.

Furthermore, players will be able to create their own quests for other users to buy and complete. These can only be completed once, and will also count towards badge progress.
A player will pay a hefty amount to create a quest, however each time another player buys said quest, the creator will receive those tokens. This is the one and only way to earn passive income in collectR.

Badges are earned, mainly, through quest progression. However, there are also 'Wealth' badges. Users can purchase these instantly and earn them by spending a large amount of tokens.
While less profitable, the requirement for making their own quests is having at least 50 tokens and 3 badges, meaning that Wealth badges are a shortcut to the passive income mentioned above.

Every player will appear in a leaderboard section, with a score calculated from their tokens and badges dynamically.
Progress can be viewed in the player's account section, both for badges and for quests.

## Getting Started

### Dependencies

Java, Spring, Maven, Thymeleaf, CSS, JavaScript, HTML
Visit the project's pom.xml for an easy source of dependencies.

### Exploring the program

The index page, while not logged in, will provide some guidelines on how to use the application. Furthermore, the user will have access to either a Register or Login page.
If registering an account, a valid e-mail address will have to be provided, along with the account needing to be verified through e-mail.

Either by registering an account or already having one, a user can then log in. Upon successfully logging in, they will be met with the full index page.
From here, they can access their Account page(top right, clicking on their name), the Shop page(navigation bar at the top), the Scoreboard page and the Games page.

The shop will provide a list of all Quests and Badges, along with the ability to purchase them if the user has enough tokens. A newly registered user receives 10 tokens to start with, enough to buy a tier 1 quest.

The scoreboard will provide a layout of all users, ranked by a score composed of tokens and badges through a simple mathematical formula.

The games page will provide access to the two main games of the app. Completing these will provide quest progress, quest completions and badge progress as well as completions.
These games can be played without quests as well, to pass the time, however there is no token reward attached.

As an Admin, which can only be designated by other Admins, the user will have access to the Admin page. Here they can add quests, badges, manage users as well as add them.
The admin page is a dynamic way to add more SYSTEM created quests and badges, with the ability to tie them to an already existing game or wealth(in the case of badges).

## Sources

- https://spring.io/ (project initializer, class information)

- https://mvnrepository.com/ (project dependencies, documentations)

- https://github.com/PaulCostache98/ProjectITSchool/ (own project)

- https://stackoverflow.com/ (troubleshooting)

- https://www.w3schools.com/ (CSS and HTML styling)

- https://data.world/ (CSV material)

- https://png.pngtree.com/png-vector/20210903/ourmid/pngtree-trivia-poster-png-image_3862027.jpg (Trivia Image)
- https://cdn-icons-png.flaticon.com/512/8309/8309198.png (Miner Image)
- https://w7.pngwing.com/pngs/919/532/png-transparent-bronze-medal-bronze-medal-gold-medal-medal-medal-gold-material-thumbnail.png (Bronze Badge Icon)
- https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIn7VZ3rkdURXL8ahGcueKzZlKOzuGix1aryunqaOx4VfpqZ9n2NEINhNseAb5-DFlnnA&usqp=CAU (Wealth Badge Icon)

## Authors

Paul Costache, @PaulC98
