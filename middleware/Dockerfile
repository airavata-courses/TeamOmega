FROM cusspvz/node:5.1.1


RUN apk add --no-cache git \
	&& apk add --no-cache python \
  	&& rm -rf /var/cache/apk/*

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app


# Install app dependencies
COPY package.json /usr/src/app/
RUN apk update && \
  	apk add $BUILD_APKS && \
	npm install && \
  	apk del $BUILD_APKS

# Bundle app source
COPY . /usr/src/app

EXPOSE 3000
CMD [ "npm", "start"]