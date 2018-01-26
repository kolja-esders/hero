
def create_plain_user(
        name="User", profile_id="",
        s_tactics=0, s_teamplay=0, s_endurance=0, s_motivation=0, s_style=0,
        email="", fb_id="", profile_pic="", friends=(), star_face = 0, star_post = 0, star_url = "", star_name="",
        position = (0,0), fb_token=""
        ):


    user = dict(
        name=name,
        profile_id=profile_id,
        skills=dict(
            tactics=s_tactics,
            teamplay=s_teamplay,
            endurance=s_endurance,
            motivation=s_motivation,
            style=s_style
        ),
        email=email,
        fb_id=fb_id,
        profile_pic_url=profile_pic,
        friends=friends,
        star=dict(
            star_name=star_name,
            face_sim=star_face,
            post_sim=star_post,
            url=star_url
        ),
        position=position,
        fb_token=fb_token
    )

    return user