from collections import defaultdict

from helpers.fb.scraper import get_posts_for_uid
from helpers.text_analysis.extract import extract_keywords
from helpers.utils import PROJ_PATH

CATS = ["activities", "devices", "injury", "locations", "lifestyle"]


class TextClassifier(object):

    def __init__(self):

        self.keyword_dict = dict()
        self.cat_dict = dict()
        self.keywords = []

        for category in CATS:

            file_name = PROJ_PATH + "/helpers/profiler/" + category + ".txt"

            with open(file_name) as f:
                for line in f.readlines():
                    if line.strip():
                        cls, keywrds = line.strip().split(":")
                        keywrds = keywrds.split(",")
                        for keywrd in keywrds:
                            self.keywords.append(keywrd)
                            self.keyword_dict[keywrd] = cls
                        self.cat_dict[cls] = category

    def classify_text(self, text):

        if text is None:
            return {}

        ret_dict = defaultdict(list)

        for keyword in self.keywords:
            if keyword.strip().lower() in text.lower():

                cls = self.keyword_dict[keyword]
                cat = self.cat_dict[cls]
                ret_dict[cat].append(cls)

        return ret_dict



def get_person_profile_light(access_token):

    target = "me"
    posts = get_posts_for_uid(target, access_token=access_token)
    # images = get_images_for_uid(target, access_token=access_token)
    # infos = get_personal_infos_for_id(target, access_token=access_token)

    keywords = extract_keywords(" ".join(posts))

    return keywords
