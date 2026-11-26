@echo off
setlocal enabledelayedexpansion

set "MODID=rustic_harvest"
set "ASSETS=src\main\resources\assets\%MODID%"
set "DATA=src\main\resources\data\%MODID%"

set /p CROP="Enter the crop registry name (e.g. barley): "
if "%CROP%"=="" exit /b

echo.
echo Generating JSON files for "%CROP%"...

:: Create directories if they do not exist
mkdir "%ASSETS%\blockstates" 2>nul
mkdir "%ASSETS%\models\block" 2>nul
mkdir "%ASSETS%\models\item" 2>nul
mkdir "%DATA%\loot_table\blocks" 2>nul
mkdir "%DATA%\worldgen\configured_feature" 2>nul
mkdir "%DATA%\worldgen\placed_feature" 2>nul
mkdir "%DATA%\worldgen\biome_modifier" 2>nul

:: 1. Blockstate
(
echo {
echo   "variants": {
echo     "age=0": { "model": "%MODID%:block/%CROP%_crop_stage0" },
echo     "age=1": { "model": "%MODID%:block/%CROP%_crop_stage1" },
echo     "age=2": { "model": "%MODID%:block/%CROP%_crop_stage2" },
echo     "age=3": { "model": "%MODID%:block/%CROP%_crop_stage3" },
echo     "age=4": { "model": "%MODID%:block/%CROP%_crop_stage4" },
echo     "age=5": { "model": "%MODID%:block/%CROP%_crop_stage5" },
echo     "age=6": { "model": "%MODID%:block/%CROP%_crop_stage6" },
echo     "age=7": { "model": "%MODID%:block/%CROP%_crop_stage7" }
echo   }
echo }
) > "%ASSETS%\blockstates\%CROP%_crop.json"

:: 2. Block Models (Stages 0-7 using minecraft:block/crop)
for /L %%i in (0,1,7) do (
    (
    echo {
    echo   "parent": "minecraft:block/crop",
    echo   "render_type": "minecraft:cutout",
    echo   "textures": {
    echo     "crop": "%MODID%:block/%CROP%_crop_stage%%i"
    echo   }
    echo }
    ) > "%ASSETS%\models\block\%CROP%_crop_stage%%i.json"
)

:: 3. Item Models (Crop item and Seeds)
(
echo {
echo   "parent": "minecraft:item/generated",
echo   "textures": {
echo     "layer0": "%MODID%:item/%CROP%"
echo   }
echo }
) > "%ASSETS%\models\item\%CROP%.json"

(
echo {
echo   "parent": "minecraft:item/generated",
echo   "textures": {
echo     "layer0": "%MODID%:item/%CROP%_seeds"
echo   }
echo }
) > "%ASSETS%\models\item\%CROP%_seeds.json"

:: 4. Block Loot Table
(
echo {
echo   "type": "minecraft:block",
echo   "pools": [
echo     {
echo       "rolls": 1.0,
echo       "entries": [
echo         {
echo           "type": "minecraft:item",
echo           "name": "%MODID%:%CROP%",
echo           "conditions": [
echo             {
echo               "condition": "minecraft:block_state_property",
echo               "block": "%MODID%:%CROP%_crop",
echo               "properties": {
echo                 "age": "7"
echo               }
echo             }
echo           ]
echo         }
echo       ],
echo       "functions": [
echo         {
echo           "function": "minecraft:apply_bonus",
echo           "formula": "minecraft:binomial_with_bonus_count",
echo           "parameters": {
echo             "extra": 3,
echo             "probability": 0.5714286
echo           },
echo           "enchantment": "minecraft:fortune"
echo         },
echo         {
echo           "function": "minecraft:explosion_decay"
echo         }
echo       ]
echo     },
echo     {
echo       "rolls": 1.0,
echo       "entries": [
echo         {
echo           "type": "minecraft:item",
echo           "name": "%MODID%:%CROP%_seeds"
echo         }
echo       ],
echo       "functions": [
echo         {
echo           "function": "minecraft:apply_bonus",
echo           "formula": "minecraft:binomial_with_bonus_count",
echo           "parameters": {
echo             "extra": 3,
echo             "probability": 0.5714286
echo           },
echo           "enchantment": "minecraft:fortune"
echo         },
echo         {
echo           "function": "minecraft:explosion_decay"
echo         }
echo       ]
echo     }
echo   ],
echo   "random_sequence": "%MODID%:blocks/%CROP%_crop"
echo }
) > "%DATA%\loot_table\blocks\%CROP%_crop.json"

echo.
echo Done! All JSON files for "%CROP%" have been generated.
echo Remember to add your .png textures and entries in en_us.json!
pause