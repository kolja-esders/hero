




# with open("keywords.txt") as f:
#     keywords = f.readlines()

from helpers.text_analysis.keywords import _keywords as keywords


def extract_keywords(text):

    extr_keywords = []

    for keyword in keywords:
        if keyword.strip().lower() in text.lower():
            extr_keywords.append(keyword.strip())

    return extr_keywords

