FROM node
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY . $HOME 
RUN npm install
EXPOSE 3000
ENTRYPOINT [ "npm", "start" ]