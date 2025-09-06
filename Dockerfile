FROM gradle:8.11.1-jdk-21-and-23

RUN apt-get update && apt-get install -qq -y --no-install-recommends

ENV INSTALL_PATH /storefront
RUN mkdir $INSTALL_PATH
WORKDIR $INSTALL_PATH

# copia TUDO da raiz (inclusive a pasta storefront e o start-dev.sh)
COPY . .

# só o script precisa de permissão aqui
RUN chmod +x ./start-dev.sh
