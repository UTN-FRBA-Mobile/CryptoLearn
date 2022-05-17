FROM python:3.9-alpine
COPY app /app/
RUN pip3 install -r /app/requirements.txt
CMD [ "python3", "-m", "app" ]