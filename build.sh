#!/usr/bin/env bash
# Build snake-java. Produces out/production/snake-java/ and snake-java.jar
#
#   ./build.sh          compile, package, then run the game
#   ./build.sh --no-run  compile and package only (used by CI)
set -euo pipefail
cd "$(dirname "$0")"

OUT=out/production/snake-java
JAR=snake-java.jar

rm -rf out "$JAR"
mkdir -p "$OUT"

# 1. compile every source file
find src -name '*.java' -print0 | xargs -0 javac -d "$OUT"

# 2. copy sprites next to the classes.
#    javac only handles .java files, and the game resolves its images with
#    getClass().getResource(...) -- package-relative on the classpath. Without
#    this step every ImageIO.read(...) call gets null and the game dies.
(cd src && find . \( -name '*.png' -o -name '*.jpg' \) \
    -exec cp --parents {} "../$OUT/" \;)

# 3. package a double-clickable jar (sprites travel inside it)
jar --create --file "$JAR" --main-class Main -C "$OUT" .

echo "built $JAR"

[ "${1:-}" = "--no-run" ] && exit 0
exec java -jar "$JAR"
