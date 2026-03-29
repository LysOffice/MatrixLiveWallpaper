# Matrix Live Wallpaper — Android

Fond d'écran animé Matrix pour Android (API 26+).

## Compiler l'APK

### Option A — Android Studio (recommandé)
1. Ouvrir Android Studio
2. `File → Open` → sélectionner ce dossier
3. Attendre la sync Gradle
4. `Build → Generate Signed / Debug APK`
5. L'APK est dans `app/build/outputs/apk/debug/`

> **Note :** Au premier lancement, Android Studio va télécharger le vrai
> `gradlew`. Remplacer le fichier `gradlew` stub par celui généré.
> Ou lancer dans le terminal : `gradle wrapper --gradle-version 8.4`

### Option B — GitHub Actions (sans Android Studio)
1. Créer un repo GitHub et pousser ce projet
2. Aller dans l'onglet **Actions**
3. Lancer le workflow **Build APK**
4. Télécharger l'artifact `matrix-wallpaper-debug.apk`

## Installer sur le téléphone
1. Activer **Sources inconnues** dans Paramètres → Sécurité
2. Copier l'APK sur le téléphone (USB ou drive)
3. Ouvrir le fichier APK → Installer
4. Aller dans **Paramètres → Fond d'écran → Fond d'écran animé**
5. Sélectionner **Matrix Live Wallpaper**

## Structure du projet
```
app/src/main/
├── assets/
│   └── matrix.html          ← Animation Matrix (modifiable)
├── java/com/matrix/wallpaper/
│   ├── MatrixWallpaperService.java  ← Service WallpaperService
│   └── PreviewActivity.java         ← Lance le sélecteur
├── res/
│   ├── xml/wallpaper.xml    ← Metadata fond d'écran
│   └── values/strings.xml
└── AndroidManifest.xml
```

## Personnaliser l'animation
Modifier `app/src/main/assets/matrix.html` :
- `FONT_SIZE` : taille des caractères
- `INTERVAL` : vitesse (ms entre frames, plus grand = plus lent)
- `CHARS` : jeu de caractères utilisé
