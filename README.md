# BlackJackGame# Jeu de Blackjack avec librairie cartes

## Université et contexte
- **Université :** Université de Caen Normandie  
- **Licence :** Licence 3 Informatique  
- **Devoir :** Contrôle Continu  
- **Date :** 5 décembre 2025  

**Auteurs :**  
- ASIM Muhammed  
- TATAH Sandra  
- HADJ BENABDELMOULA Lamia  
- KHELALFA Selssabil  

---

## Résumé
Ce projet consiste en un jeu de **Blackjack** utilisant une architecture **MVC** et une librairie de cartes réutilisable.  
L’application met en œuvre plusieurs **design patterns** pour assurer :
- séparation claire des responsabilités  
- maintenabilité du code  
- extensibilité et réutilisation de la librairie cartes  

Le système de build **Ant** fourni facilite la compilation, l’exécution et la génération de la documentation.

---

## Objectifs
- Implémenter un jeu de Blackjack fonctionnel avec interface graphique et logique métier complète.  
- Créer une **librairie de cartes réutilisable** pour divers jeux de cartes.  
- Appliquer les **design patterns** pour structurer et maintenir le code.  
- Intégrer des **IA avec différentes stratégies** pour le jeu.  

---

## Architecture du projet
L’application suit le pattern **MVC** :

src/
├── cartes/ # Librairie cartes réutilisable
│ ├── Carte.java
│ ├── CarteStandard.java
│ ├── Paquet.java
│ ├── PaquetStandard.java
│ ├── FabriqueJeu.java
│ ├── FabriqueJeuStandard.java
│ └── VueCarte.java / VueCarteGUI / VueCarteConsole
├── blackjack/
│ ├── modele/ # Logique métier
│ ├── vue/ # Interfaces graphiques et consoles
│ ├── controleur/ # Contrôleurs MVC
│ └── observer/ # Observer pattern
├── test/ # Tests unitaires
└── assets/ # Images des cartes et ressources


---

## Design Patterns utilisés
- **MVC (Model-View-Controller)** : séparation des responsabilités  
- **Observer** : mise à jour automatique des vues  
- **Strategy** : IA avec différentes stratégies de jeu  
- **Factory Method** : création flexible de jeux de cartes  
- **State** : gestion des différents états du jeu  
- **Template Method** : définition du flux de tours de jeu pour les joueurs  

---

## Fonctionnalités
- **Librairie cartes** : création, mélange, distribution, pioche  
- **Interface graphique** : Swing, feedback en temps réel  
- **Intelligence artificielle** : différentes stratégies adaptatives  
- **Gestion des états** : orchestrée via des machines à états, respect des règles du Blackjack  

---

## Compilation et exécution
1. **Compiler le projet :**  
```bash
ant compile

    Lancer la version graphique :

ant runGUI

    Exécuter les tests unitaires :

ant test

    Générer la documentation :

ant doc

    Créer la distribution complète :

ant dist

Règles du Blackjack implémentées

    Valeurs des cartes : As (1 ou 11), Figures (10), autres cartes : valeur nominale

    Blackjack naturel : 21 avec deux cartes (As + Figure)

    Croupier tire jusqu’à 16 et reste à 17 ou plus

    Paiements : Blackjack 3:2, main gagnante 1:1

    Pas de split ni d’assurance

Perspectives d’évolution

    Mode multijoueur réseau

    IA plus avancée avec apprentissage

    Animations et feedbacks sonores

    Portage sur mobile
