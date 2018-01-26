from django.db import models

# Create your models here.

class Superstar(models.Model):
    name = models.CharField(max_length=200)
    fid = models.CharField(max_length=200)
    url = models.CharField(max_length=350)
    face_id = models.CharField(max_length=350, default="    ")
    keywords = models.TextField()

