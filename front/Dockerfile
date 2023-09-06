#
# Build
#
FROM node:14.15.4 AS build
WORKDIR /app
COPY ./aggregator .
RUN npm install && \
        npm run build
#
# Run
#
FROM nginx:alpine
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/aggregator/* /usr/share/nginx/html/