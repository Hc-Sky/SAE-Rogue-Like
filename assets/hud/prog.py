from PIL import Image
import os

def decouper_image(nom_image, nb_colonnes, nb_lignes):
    image = Image.open(nom_image).convert("RGBA")
    largeur, hauteur = image.size
    largeur_carreau = largeur // nb_colonnes
    hauteur_carreau = hauteur // nb_lignes

    os.makedirs("resultat", exist_ok=True)

    compteur = 1
    for i in range(nb_lignes):
        for j in range(nb_colonnes):
            gauche = j * largeur_carreau
            haut = i * hauteur_carreau
            droite = (j + 1) * largeur_carreau
            bas = (i + 1) * hauteur_carreau
            carreau = image.crop((gauche, haut, droite, bas))
            nom_carreau = f"resultat/{compteur-1}.png"
            carreau.save(nom_carreau)
            compteur += 1

decouper_image("stamina.png", 7, 1)