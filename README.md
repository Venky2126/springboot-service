# springboot-service
projects
=========
1.one-time-password
https://www.pixeltrice.com/send-otpone-time-password-using-spring-boot-application-for-authentication/

2.recaptcha-validation
https://www.pixeltrice.com/recaptcha-validation-in-registration-form-using-spring-boot-application/

3.chat-application
https://www.pixeltrice.com/build-spring-boot-chat-application-from-scratch/

4.integrate-facebook
https://www.pixeltrice.com/integrate-facebook-for-social-login-in-your-spring-boot-application/



Open issues & Solutions
1. $ git push origin feature/basic-setup
remote: Permission to Venky2126/springboot-service.git denied to venky4026.
fatal: unable to access 'https://github.com/Venky2126/springboot-service.git/': The requested URL returned error: 403


Solution : follow the below step it will fix above issue 100% worked.

The error message indicates that there is an issue with SSH key authentication. Here are the steps to resolve this issue:

Step 1: Check if you have an SSH key
First, check if you already have an SSH key generated on your local machine. You can do this by running the following command:

sh
ls -al ~/.ssh
Look for files named id_rsa and id_rsa.pub or similar. If you don't have these files, you need to generate a new SSH key.

Step 2: Generate a new SSH key (if needed)
If you don't have an SSH key, generate one by running:

sh
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
Follow the prompts and use the default file location. You can optionally set a passphrase for added security.

Step 3: Add your SSH key to the SSH agent
Start the SSH agent and add your SSH key:

sh
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
Step 4: Add your SSH key to your GitHub account
Copy the SSH key to your clipboard:

sh
cat ~/.ssh/id_rsa.pub
Log in to your GitHub account and go to SSH and GPG keys. Click on "New SSH key" and paste your SSH key.

Step 5: Test your SSH connection
Test your SSH connection to GitHub:

sh
ssh -T git@github.com
You should see a message like:

Code
Hi Venky2126! You've successfully authenticated, but GitHub does not provide shell access.