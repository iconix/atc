def rand_in_range(from, to)
  rand * (to - from) + from
end

# return longitude within Stanford
def rand_longitude
  "-122.1%d" % rand(610382..808111)
end

# return latitude within Stanford
def rand_latitude
  "37.4%d" % rand(210574..356318)
end

namespace :db do
  desc "Fill database with sample data"
  task populate: :environment do
    17.times do
      email = Faker::Internet.email
      name = Faker::Company.name
      password = "password"
      Business.create!(email: email,
                       name: name,
                       password: password,
                       password_confirmation: password)
    end
    tap = Business.create!(email: "themanagement@tap.org",
                           name: "The Axe & Palm",
                           password: "foobar",
                           password_confirmation: "foobar")
    ikes = Business.create!(email: "themanagement@ilikeikesplace.com",
                            name: "Ike's Sandwiches @ Stanford",
                            password: "foobar",
                            password_confirmation: "foobar")
    coupa = Business.create!(email: "themanagement@coupacafe.com",
                             name: "Coupa Cafe @ Green Library",
                             password: "foobar",
                             password_confirmation: "foobar")

    tap.deals.create!(longitude: -122.1703289,
                      latitude: 37.4251203,
                      startDate: 2.weeks.ago,
                      endDate: 1.week.from_now,
                      title: "50% off milkshakes!!!")
    ikes.deals.create!(longitude: -122.1749263,
                       latitude: 37.427662,
                       startDate: 2.days.ago,
                       endDate: 1.day.from_now,
                       title: "Buy One Sandwich, Get One Free")
    coupa.deals.create!(longitude: -122.1661241,
                        latitude: 37.4253463,
                        startDate: 1.week.ago,
                        endDate: Time.now,
                        title: "Free Drip Coffee (with purchase of a pastry)")

    businesses = Business.all(limit: 7)
    3.times do
      businesses.each { |business|
        longitude = rand_longitude
        latitude = rand_latitude
        startDate = Time.at(rand_in_range(1.year.ago.to_f, Time.now.to_f))
        endDate = Time.at(rand_in_range(Time.now.to_f, 1.year.from_now.to_f))
        title = Faker::Lorem.sentence(3)

        business.deals.create!(longitude: longitude,
                               latitude: latitude,
                               startDate: startDate,
                               endDate: endDate,
                               title: title) 
      }
    end
  end
end
