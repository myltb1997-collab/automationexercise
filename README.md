This is my demo automation testing.

Web practice: https://automationexercise.com

Project about: Selenium, Maven, TestNG with Java. Design pattern POM + Allure report. Deploy in Jenkins.

**Deploy in Jenkins:**

1: Add item -> input name, click to Maven project option -> Ok btn
<img width="1094" height="597" alt="image" src="https://github.com/user-attachments/assets/1c760e8c-9e25-4d1b-b2fd-1ceb71717a33" />
2: Configuration 

- Source Code Management -> input the link of GitHub 
  <img width="1222" height="533" alt="image" src="https://github.com/user-attachments/assets/412dd835-45ae-48ee-a796-2473ef5f8ccc" />

3: Trigger time
<img width="913" height="537" alt="image" src="https://github.com/user-attachments/assets/ae6b8ef4-a0f2-4dc2-ac7f-4dd0f7a9e490" />

4:Build 
<img width="903" height="305" alt="image" src="https://github.com/user-attachments/assets/a409009a-c183-4f16-bcc8-52cd9fb66777" />

5: Post step

<img width="808" height="312" alt="image" src="https://github.com/user-attachments/assets/8e9158a3-faf3-40ca-b6ad-40608f31c912" />

6: OK to save all config



**Add the Allure report in Jenkins**

1: Installed plugins Allure Jenkins Plugin Version 2.32.0
<img width="1049" height="271" alt="image" src="https://github.com/user-attachments/assets/07cd82c3-0bed-40e4-8c26-5838dc881bc2" />

2: Manage Jenkins -> Tools: Install Maven + Allure 
<img width="608" height="523" alt="image" src="https://github.com/user-attachments/assets/5b9019ef-7643-4dca-93b9-b8f0d23c08c2" />
<img width="702" height="413" alt="image" src="https://github.com/user-attachments/assets/93d3c67e-3635-413c-a321-eec1ac1e3772" />

3: In build -> Configure -> Post build action
<img width="875" height="433" alt="image" src="https://github.com/user-attachments/assets/7ff6c1bc-7563-42da-b2c5-821686f5fae8" />

**Result after build**

<img width="1328" height="515" alt="image" src="https://github.com/user-attachments/assets/e3cd5243-ac3b-4029-9180-d88736ea067e" />
<img width="774" height="520" alt="image" src="https://github.com/user-attachments/assets/8e0bf634-c33a-4d64-8d4d-afe603fa2491" />









