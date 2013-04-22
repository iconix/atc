namespace :db do
  task :everything => [:environment, :drop, :create, :migrate, :admins, :groupon, :eightcoupons] do
    desc "Recreate everything from scratch including pre-populated data"
  end
end
