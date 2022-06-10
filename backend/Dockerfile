FROM python:3.9-alpine
WORKDIR /app
COPY /app/requirements.txt .
RUN pip3 install -r /app/requirements.txt
COPY . .
CMD [ "python3", "-m", "app" ]