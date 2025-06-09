# WashNow
A web-based platform for car-wash bays, designed for ease of use and for an efficient deployment, our system brings digital convenience to a traditionally offline service.

Thus, allowing drivers to easily locate available bays, reserve one or more time‑slots, unlock the jet‑wash upon arrival, run the washing program, pay automatically, and retrieve receipts, all within the comfort of our single user-friendly interface, and available for all types of car washes. 

 Car wash Operators/Owners on the other hand benefit from now having a dashboard to create and manage bays, define schedules and prices, while also monitoring water and energy consumption (available only on a more advanced part of the app development)  

# Team Members Roles

| Name            | Nº Mec | Role |
|-----------------|--------|-------|
| Ricardo Martins | 112876 | Team Coordinator |
| André Vasques   | 113613 | Product Owner |
| Rodrigo Jesus   | 113526 | QA Engineer |
| José Pedro      | 113403 | DevOps Master |

# Relevant Sources
[JIRA Backlog Page](https://jpsopedro04.atlassian.net/jira/software/projects/MBA/boards/1?atlOrigin=eyJpIjoiZTJhNTYxNzg0MWZhNGExNDg1ZjNhZjM3ZjA0NzQ5ZDUiLCJwIjoiaiJ9)

[SonarCloud Page](https://sonarcloud.io/summary/new_code?id=WashNow_WashNow)

## Local Pages
[Documentation Page](http://localhost:8080/swagger-ui/index.html)

[Grafana Dashboards Page](http://localhost:3000)

## Remote Pages in the VM
[Documentation Page](http://deti-tqs-11.ua.pt:8080/swagger-ui/index.html)

[Grafana Dashboards Page](http://deti-tqs-11.ua.pt:3000)


## Acessing our Grafana Dashboards Page 
    Username: admin
    Password: SenhaTeste123!


# How to run the code
Todo o nosso sistema está a correr na VM graças à nossa pipeline de CI/CD que verifica a qualidade do código que quando enviado para a branch da main é depois disso automaticamente colocado dentro da nossa VM

## Running locally 
Apesar disso se o objetivo for correr o código localmente basta abrir o diretório principal do projeto e:
1. Primeiro navegar para dentro do diretório do frontend e alterar os IPs dentro do ficheiro de nginx.conf para o IP do computador local
2. Depois basta correr o comando de docker compose up [--build], esperar que ele descarrega todos os ficheiros e dê up dos containers e está feito, todo o sistema a correr por completo localmente.


# Badges

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=WashNow_WashNow&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=WashNow_WashNow)

# Latest Changes
Após a aula de quinta feira as mudanças principais que fizemos englobaram de forma geral:
1. Rever os Pull Requests que já tinham sido criados mas que precisaram ainda de mais uns bug fixes e pequenas mudanças para estarem de acordo com o código na branch principal e passarem nos testes.
2. Atualizar o README e outra documentação relativa ao projeto, nomeadamente o push do ficheiro pdf da apresentação.
3. Adicionar mais robustez ao código do backend ao fazer verificações das reservas e melhorias e na lógica de criação das mesmas, impondo limites.
4. Adicionar então mais testes que agora garantissem na mesma que esta nova lógica de reservas continuava a funcionar. Para isso usamos alguns de testes unitários e funcionais.
5. Atualização da informação que é mostrada nas páginas de frontend conforme o tipo de utilizador.
