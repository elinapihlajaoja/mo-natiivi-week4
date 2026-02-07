Linkki YouTube-videoon: https://youtu.be/1t5J-nJYXvg

# Week 1
## Datamalli
"Task" data class:

- id = tehtävän tunniste 
- title = otsikko 
- description = kuvaus
- priority = tärkeys
- dueDate = määräpäivä 
- done/not done = tehtävän tila (valmis/kesken) 

## Funktiot
- addTask = Lisää uuden tehtävän
- toddleDone = Vaihtaa tehtävän tilan (valmis/kesken) 
- filterByDone = Valitsee tehtävistä ne, jotka ovat valmiita
- sortByDueDate = Järjestää tehtävät päivämäärän mukaan

# Week 2

## Compose-tilanhallinta:
Jetpack Composessa UI reagoi tilaan. Kun tila muuttuu, Compose piirtää vain tarvittavat osat uudelleen automaattisesti.

## Miksi ViewModel on parempi kuin remember:
**remember** säilyttää tilan vain niin kauan kuin näkymä on muistissa, ja se katoaa esimerkiksi näytön käännössä.
**ViewModel** säilyttää tilan pidempään, selviää konfiguraatiomuutoksista ja pitää sovelluksen logiikan erillään käyttöliittymästä.

# Week 3

**MVVM**: Model-View-ViewModel on arkkitehtuurimalli, jossa Model sisältää datan ja logiikan, View näyttää käyttöliittymän ja ViewModel hallitsee tilaa ja välittää datan View:lle.

Se on hyödyllinen Compose-sovelluksissa, koska ViewModel säilyy konfiguraatiomuutoksissa, erottaa UI-logiikan datasta ja mahdollistaa automaattiset päivitykset Compose-komponenteissa.

**StateFlow** on Kotlinin Flow, joka pitää yllä viimeisintä arvoa ja lähettää muutokset automaattisesti kuuntelijoille. UI voi kerätä StateFlow’n ja päivittyä automaattisesti, kun tila muuttuu.

# Week 4

**Navigointi Jetpack Composessa** tarkoittaa siirtymistä eri Composable-näkymien välillä ilman aktiviteettien vaihtoa. Näytöllä näkyvä sisältö vaihtuu sovelluksen tilan ja reittien perusteella.

**NavController** vastaa navigoinnista ja backstackin hallinnasta sekä suorittaa näkymän vaihdot.

**NavHost** määrittelee sovelluksen navigointirakenteen ja yhdistää reitit niitä vastaaviin Composableihin.


**Sovelluksen navigaatiorakenne**

- Sovelluksessa MainActivity sisältää NavHostin, jossa on määritelty reitit Home ja Calendar. Home-näkymän kalenteri-ikonista siirrytään Calendar-näkymään NavControllerin avulla, ja paluu tapahtuu takaisin stackin kautta.

**Arkkitehtuuri (MVVM)**

- Sovellus noudattaa MVVM-arkkitehtuuria, jossa yksi jaettu TaskViewModel luodaan MainActivityssa ja välitetään sekä Home- että Calendar-näkymälle.

 - ViewModel hallitsee sovelluksen tilaa StateFlown avulla. Koska molemmat ruudut tarkkailevat samaa tilaa, tehtävien lisäys tai muokkaus näkyy välittömästi kummassakin näkymässä. Kaikki liiketoimintalogiikka on keskitetty ViewModeliin.

**CalendarScreen**

- CalendarScreenissä tehtävät ryhmitellään päivämäärän mukaan ja esitetään listana, jossa jokaisella päivämäärällä on oma otsikko. Tämän alle näytetään kyseiseen päivään kuuluvat tehtävät, mikä luo kalenterimaisen näkymän.

**AlertDialog (Add & Edit)**

- Uuden tehtävän lisääminen ja olemassa olevan muokkaaminen tapahtuvat AlertDialogin kautta. Dialogit keräävät käyttäjän syötteen ja kutsuvat ViewModelin metodeja (addTask ja updateTask), jotka päivittävät sovelluksen tilan.
