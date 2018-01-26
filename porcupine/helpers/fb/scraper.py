

### /home/david/reps/your-insurance/server/your_insurance/helpers

"""
A simple example script to get all posts on a user's timeline.
Originally created by Mitchell Stewart.
<https://gist.github.com/mylsb/10294040>
"""
from urllib.parse import urlencode
from urllib.request import urlopen

import facebook
import requests




import os
import json
import urllib
import pprint

# get Facebook access token from environment variable
HOST = "https://graph.facebook.com"

N_IMGS = 10
N_POSTS = 50


# build the URL for the API endpoint


def get_profile_pic(id, access_token):


    path='/'+ str(id) + '/picture'


    params = urlencode(dict(
        height="300",
        access_token=access_token
    ))

    url = "{host}{path}?{params}".format(host=HOST, path=path, params=params)

    # open the URL and read the response
    resp = requests.get(url)

    return resp.url


def get_img_url_for_id(id, access_token):


    path='/'+ str(id)


    params = urlencode(dict(
        fields='images',
        access_token=access_token
    ))

    url = "{host}{path}?{params}".format(host=HOST, path=path, params=params)

    # open the URL and read the response
    resp = urlopen(url).read()

    str_response = resp.decode('utf-8')
    img_dict = json.loads(str_response)


    img_str = img_dict['images'][0]['source']

    # convert the returned JSON string to a Python datatype
    # me = json.loads(resp)


    # display the result
    #pprint.pprint(img_str)

    return img_str


def get_posts_for_uid(uid, access_token):

    graph = facebook.GraphAPI(access_token)
    profile = graph.get_object(uid)
    posts = graph.get_connections(profile['id'], 'posts')

    ret_posts = []

    for i in range(N_POSTS):
        try:
            # Perform some action on each post in the collection we receive from
            # Facebook.
            for post in posts['data']:
                if "message" in post:
                    ret_posts.append(post['message'])

            # Attempt to make a request to the next page of data, if it exists.
            posts = requests.get(posts['paging']['next']).json()
        except KeyError:
            # When there are no more pages (['paging']['next']), break from the
            # loop and end the script.
            break


    return ret_posts


def get_fb_email(id, access_token):

    path='/'+ str(id)

    person_dict = dict()


    params = urlencode(dict(
        fields="email",
        access_token=access_token
    ))

    url = "{host}{path}?{params}".format(host=HOST, path=path, params=params)

    try:

        # open the URL and read the response
        resp = urlopen(url).read()

        str_response = resp.decode('utf-8')
        field_dict = json.loads(str_response)

        email = field_dict["email"]

    except:
        email=None

    return email

def get_fb_name(id, access_token):

    path='/'+ str(id)

    params = urlencode(dict(
        fields="name",
        access_token=access_token
    ))

    url = "{host}{path}?{params}".format(host=HOST, path=path, params=params)

    try:

        # open the URL and read the response
        resp = urlopen(url).read()

        str_response = resp.decode('utf-8')
        field_dict = json.loads(str_response)

        name = field_dict["name"]

    except:
        name = ""

    return name




def get_personal_infos_for_id(id, access_token):

    path='/'+ str(id)

    infos_fields = ["about","location","affiliation","birthday","name","email","hometown","locations","website", "gender", "work", "picture"]

    person_dict = dict()

    for i_f in infos_fields:

        params = urlencode(dict(
            fields=i_f,
            access_token=access_token
        ))

        url = "{host}{path}?{params}".format(host=HOST, path=path, params=params)

        try:

            # open the URL and read the response
            resp = urlopen(url).read()

            str_response = resp.decode('utf-8')
            field_dict = json.loads(str_response)

            person_dict[i_f] = field_dict[i_f]


        except:
            pass


    return person_dict


def get_likes_for_uid(uid, access_token):

    graph = facebook.GraphAPI(access_token)
    profile = graph.get_object(uid)
    likes = graph.get_connections(profile['id'], 'likes')

    ret_likes = []

    for i in range(N_POSTS):
        try:
            # Perform some action on each post in the collection we receive from
            # Facebook.
            for like in likes['data']:
                ret_likes.append(like['name'])

            # Attempt to make a request to the next page of data, if it exists.
            likes = requests.get(likes['paging']['next']).json()
        except KeyError:
            # When there are no more pages (['paging']['next']), break from the
            # loop and end the script.
            break

    return ret_likes


def get_images_for_uid(uid, access_token):

    graph = facebook.GraphAPI(access_token)
    profile = graph.get_object(uid)
    photos = graph.get_connections(profile['id'], 'photos')

    ret_imgs = []

    for i in range(N_IMGS):
        try:
            # Perform some action on each post in the collection we receive from
            # Facebook.
            for photo in photos['data']:
                ret_imgs.append(dict(
                    img_url = get_img_url_for_id(photo['id'], access_token),
                    name = photo.get("name")
                ))

            # Attempt to make a request to the next page of data, if it exists.
            photos = requests.get(photos['paging']['next']).json()
        except KeyError:
            # When there are no more pages (['paging']['next']), break from the
            # loop and end the script.
            break

    return ret_imgs


def get_family_for_uid(uid, access_token):

    graph = facebook.GraphAPI(access_token)
    profile = graph.get_object(uid)
    fam_members = graph.get_connections(profile['id'], 'family')

    members = []

    for i in range(N_IMGS):
        try:
            # Perform some action on each post in the collection we receive from
            # Facebook.
            for member in fam_members['data']:

                member_info = get_personal_infos_for_id(member['id'], access_token)

                members.append(dict(
                    name = member.get('name'),
                    birthday = member_info.get('birthday'),
                    gender = member_info.get('gender'),
                    relation = member.get('relationship')
                ))

            # Attempt to make a request to the next page of data, if it exists.
            fam_members = requests.get(fam_members['paging']['next']).json()
        except KeyError:
            # When there are no more pages (['paging']['next']), break from the
            # loop and end the script.
            break

    return members


def get_locations_for_uid(uid, access_token):

    graph = facebook.GraphAPI(access_token)
    profile = graph.get_object(uid)
    tagged_locations = graph.get_connections(profile['id'], 'tagged_places')

    locations = []

    for i in range(N_IMGS):
        try:
            # Perform some action on each post in the collection we receive from
            # Facebook.
            for loc in tagged_locations['data']:

                locations.append(dict(
                    name = loc.get('place').get('name'),
                    time = loc.get('created_time'),
                    long = loc.get('place').get('location').get('longitude'),
                    lat = loc.get('place').get('location').get('latitude'),
                ))

            # Attempt to make a request to the next page of data, if it exists.
            tagged_locations = requests.get(tagged_locations['paging']['next']).json()
        except KeyError:
            # When there are no more pages (['paging']['next']), break from the
            # loop and end the script.
            break

    return locations
    


#about,affiliation,birthday,emails,likes,hometown,locations,website


# print(get_posts_for_uid("me"))
# print(get_images_for_uid("me"))
# print(get_personal_infos_for_id("me"))
# print(get_likes_for_uid("me"))
# print(get_family_for_uid("me"))
# print(get_locations_for_uid("me"))

# graph = facebook.GraphAPI(ACCESS_TOKEN)
# profile = graph.get_object(user)
# posts = graph.get_connections(profile['id'], 'posts')
#
# photos = graph.get_connections(profile['id'], 'photos')
#
#
#
#
# # Wrap this block in a while loop so we can keep paginating requests until
# # finished.
# def some_action2(post):
#
#     photo_id = post['id']
#
#     print(photo_id)
#
#     get_img_url_for_id(photo_id)
#
#     print("\n")
#
#
#
# while True:
#     try:
#         # Perform some action on each post in the collection we receive from
#         # Facebook.
#         [some_action2(post=post) for post in photos['data']]
#         # Attempt to make a request to the next page of data, if it exists.
#         posts = requests.get(posts['paging']['next']).json()
#     except KeyError:
#         # When there are no more pages (['paging']['next']), break from the
#         # loop and end the script.
#         break
#
# # Wrap this block in a while loop so we can keep paginating requests until
# # finished.
# while True:
#     try:
#         # Perform some action on each post in the collection we receive from
#         # Facebook.
#         [some_action(post=post) for post in posts['data']]
#         # Attempt to make a request to the next page of data, if it exists.
#         posts = requests.get(posts['paging']['next']).json()
#     except KeyError:
#         # When there are no more pages (['paging']['next']), break from the
#         # loop and end the script.
#         break
