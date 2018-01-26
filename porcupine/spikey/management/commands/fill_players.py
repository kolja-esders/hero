from django.core.management import BaseCommand

from helpers.fb.scraper import get_posts_for_uid, get_profile_pic
from helpers.text_analysis.extract import extract_keywords
from spikey.models import Superstar

star_fb_ids = [
    ("Manuel Neuer", "manuel.neuer"),
    ("Arjen Robben", "ArjenRobbenpagr")
]


access_token = "EAACEdEose0cBACBn6hWGAFZCNyK9nKApvUWdoHncdkwmqnOXHuO5rzcfVZAcYL7qPM4QrAGODUbLVWmIlvOTuQ0lXtystZAtakIekPNFFjXz0r36Il979vQLw8G31ZA7nXwPAFWlCF4Sq3KZAUCLzBEEIVwI5KkA1aXcZA3Y2jw5ZA8VpuUYHLvYtuO8yLDg2oZD"


class Command(BaseCommand):

    def handle(self, *args, **options):

        for name, star_id in star_fb_ids:

            posts = get_posts_for_uid(star_id, access_token=access_token)
            keywords = extract_keywords(" ".join(posts))
            keyword_str = ";".join(keywords)

            url = get_profile_pic(star_id, access_token=access_token)

            p1 = Superstar(fid=star_id, name=name, url=url, keywords=keyword_str)
            p1.save()