# Currencify

Tech Stack, Architecture & Code Style
- App implementation tries to follow SOLID and Clean Code and Clean Architecture principles.
- App uses Dagger 2 for dependency injection purpose.
- Network operations are done via the combination of Retrofit, RxJava 3, and Moshi.
- The app uses the principles/technologies mentioned above and RxJava 3 to increase cohesion and decrease the coupling between the layers/classes.
- Although it is a bit overkill for this small task, the app tries to demonstrate the Uncle Bob's Clean Architecture. There are 5 layers in the app:
    - UI -> Dumb as much as possible, almost all of the view and presentation logic has been moved to the presentation layer. Uses ViewBinding to eliminate the boilerplate.
    - Presentation -> Implements MVVM presentational design pattern by using Android Architecture Components (ViewModel, LiveData).
    - Domain -> Pure Kotlin/Java layer that is responsible for the business/domain logic although there is not much for this app.
    - Data -> Pure Kotlin/Java layer to decouple the library dependencies of the remote layer.
    - Remote ->  Pure Kotlin/Java layer to retrieve the data from the API.

Known Flaws
- UI is not the best since I thought the main scope of this task was not the UI. I still tried to do my best and tried to improve the UX at least.
- Unit and UI tests are missing due to the time limitations I have.
- Packages do not really represent the pure clean architecture as they can access each other's content (although they do not access). I would prefer having separate modules for each layer but wanted to keep it simple since the task is not that complex.
- There might be too much boilerplate code for some developers but I would prefer to keep the readability of the code as high as possible.
- I could not make a memory leak check due to the time limitations.

Repository
- My main work can be seen on the 'development' branch.
- If you would like to see my progress please refer to this link https://github.com/tartarJR/Currencify/projects.
- Each issue/task has the linked pull request that has the corresponding changes.
- One of my close friends did the code reviews for me and you might see his name and his comments in the repository.
- He has no Android knowledge(he is an IOS dev) and he has no impact on my solution. He only helped me to demonstrate a decent development flow.

