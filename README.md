# Ticket Console Management

Application Java console pour gerer un cycle de vie de tickets (creation, recherche, assignation, changement de statut, statistiques, import/export CSV).

## Objectif du projet

Ce projet implemente un mini systeme de gestion de tickets orientee support IT:
- creation et stockage de tickets
- recherche et filtrage
- operations metier (assignation, changement de statut)
- persistance dans un fichier CSV
- interface utilisateur en ligne de commande

## Fonctionnalites

Le menu principal propose:

1. Creer un ticket
2. Afficher tous les tickets
3. Afficher un ticket par id
4. Lister les tickets par priorite
5. Assigner un ticket a un technicien
6. Changer le statut d'un ticket
7. Afficher des statistiques
8. Exporter les tickets en CSV
9. Importer des tickets depuis CSV
0. Quitter

## Architecture

Structure principale:

- `App` : point d'entree et menu console
- `TicketService` : logique metier
- `Ticket` : entite metier + regles de validation
- `TicketRepository` : contrat d'acces aux donnees
- `CSVTicketRepository` : implementation de persistance CSV
- `InMemoryTicketRepository` : implementation memoire (tests)
- `Priority`, `TicketStatus` : enums metier
- `TicketNotFoundException` : exception metier

## Regles metier implementees

- Un ticket ne peut pas etre cree avec:
  - date d'incident nulle
  - date d'incident dans le futur
  - titre vide
  - description vide
  - demandeur vide
  - service vide
- Assignation:
  - le nom du technicien est obligatoire
  - `assignedAt` et `updatedAt` sont mis a jour
- Changement de statut:
  - statut obligatoire
  - `resolvedAt` renseigne au passage a `RESOLVED`
  - `closedAt` renseigne au passage a `CLOSED`
  - `updatedAt` est mis a jour
- Recherche par id:
  - leve `TicketNotFoundException` si introuvable

## Prerequis

- Java 17
- Maven 3.8+

## Installation et execution

Depuis la racine du projet:

```bash
mvn clean compile
java -cp target/classes dhi.training.dev.App
```

Pour lancer les tests:

```bash
mvn test
```

## Format du fichier CSV

Le fichier par defaut est `tickets.csv` a la racine du projet.

En-tete attendu:

```text
id,title,description,priority,requester,service,occurredAt,openedAt,status,closedAt,resolvedAt,assignedTo,assignedAt,createdAt,updatedAt
```

Exemple de valeurs:
- dates au format ISO-8601, ex: `2026-04-22T10:30`
- priorites: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`
- statuts: `OPEN`, `PENDING`, `RESOLVED`, `CLOSED`

## Choix techniques

- Separation simple des responsabilites (UI, service, repository)
- Utilisation de `Optional` pour les recherches
- Utilisation d'une exception metier dediee pour les cas introuvables
- Persistance CSV legere sans base de donnees

## Limites actuelles

- Parsing CSV basique (`split(",")`), non adapte aux champs contenant des virgules ou des guillemets
- Pas de suppression de ticket implementee
- Gestion d'encodage perfectible (certains messages console peuvent etre mal affiches selon terminal)
- Le menu ne propose pas encore de sous-menus

## Pistes d'amelioration

- Passer a une librairie CSV robuste (OpenCSV, Apache Commons CSV)
- Ajouter la suppression et la modification de tickets
- Ajouter des tests supplementaires (assignation, status, import/export)
- Introduire un DTO `TicketStats` plutot qu'un affichage direct dans `printStatistics`
- Ajouter une couche de persistance alternative (JSON, base SQL)

## Auteur

Projet realise dans le cadre d'un apprentissage Java orientee objet et architecture en couches.
