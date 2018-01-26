from django.core.management import BaseCommand

from helpers.face.detect import get_face_id
from helpers.fb.scraper import get_posts_for_uid, get_profile_pic
from helpers.text_analysis.extract import extract_keywords
from spikey.models import Superstar


class Command(BaseCommand):

    def handle(self, *args, **options):

        for star in Superstar.objects.all():

            star_pic = star.url
            face_id = get_face_id(star_pic)

            star.face_id = face_id
            star.save()